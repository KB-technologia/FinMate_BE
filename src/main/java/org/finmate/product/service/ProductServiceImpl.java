package org.finmate.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.common.util.OpenAiApi;
import org.finmate.common.util.OpenAiDTO.OpenAiResponseDTO;
import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.dto.ProductFilterDTO;
import org.finmate.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;


    private final OpenAiApi openAiApi;
    //TODO: 필터링 서비스 구현

    @Override
    public List<ProductDTO<?>> getAllProducts() {
        return productMapper.getAllProducts()
                .stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO<?> getProductDetail(Long id) {
        return ProductDTO.from(productMapper.getProductDetail(id));
    }

    @Override
    public ProductComparisonResultDTO compareProducts(Long id1, Long id2) {
        ProductDTO<?> data1 = getProductDetail(id1);
        ProductDTO<?> data2 = getProductDetail(id2);

        //TODO: 사용자 데이터도 결합해서 데이터 구성해야됨
        String text = """
다음은 두 개의 금융 상품 데이터입니다.
각각의 상품을 이해하고, 두 상품의 특징을 자연스럽게 비교하여 설명해 주세요.

[상품1]
%s

[상품2]
%s

비교 요약:
""".formatted(data1.toString(), data2.toString());
        OpenAiResponseDTO response = openAiApi.callResponses(text);
        String comparisonResult = response.getOutput().get(0).getContent().get(0).getText();

        ProductComparisonResultDTO result = ProductComparisonResultDTO.builder()
                .product1(data1)
                .product2(data2)
                .comparisonResult(comparisonResult)
                .build();

        return result;
    }

    @Override
    public List<ProductDTO<?>> getFilteredProducts(ProductFilterDTO filter) {
        // 입력값 검증
        validateFilter(filter);

        // 상품 조회 (페이징 없이 전체 조회)
        List<org.finmate.product.domain.ProductVO> products = productMapper.getFilteredProductsByType(filter);

        // ProductDTO로 변환하여 반환
        return products.stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    private void validateFilter(ProductFilterDTO filter) {

        // 검색어 전처리
        if (filter.getQuery() != null) {
            filter.setQuery(filter.getQuery().trim());
            if (filter.getQuery().isEmpty()) {
                filter.setQuery(null);
            }
        }
    }
}

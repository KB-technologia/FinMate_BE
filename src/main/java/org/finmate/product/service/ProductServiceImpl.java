package org.finmate.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.adapter.openai.OpenAiApi;
import org.finmate.adapter.openai.dto.OpenAiResponseDTO;
import org.finmate.common.util.PromptLoader;
import org.finmate.exception.NotFoundException;
import org.finmate.member.domain.AnimalCharacterVO;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.mapper.AnimalCharacterMapper;
import org.finmate.member.mapper.UserInfoMapper;
import org.finmate.portfolio.domain.PortfolioVO;
import org.finmate.portfolio.mapper.PortfolioMapper;
import org.finmate.product.domain.ProductReviewVO;
import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.dto.ProductFilterDTO;
import org.finmate.product.dto.ProductReviewDTO;
import org.finmate.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    private final UserInfoMapper userInfoMapper;

    private final PortfolioMapper portfolioMapper;

    private final AnimalCharacterMapper animalCharacterMapper;

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
        return ProductDTO.from(
                Optional.ofNullable(productMapper.getProductDetail(id))
                        .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."))
        );
    }

    @Override
    public ProductComparisonResultDTO compareProducts(Long id1, Long id2, CustomUser user) {
        ProductDTO<?> data1 = getProductDetail(id1);
        ProductDTO<?> data2 = getProductDetail(id2);

        //비교 프롬프트 텍스트 로드
        String text = PromptLoader.load("/prompts/product_compare.txt");


        //TODO: 나머지 5개 수치 추천 알고리즘 점수로 추가로 넣으면 좋을듯
        String userData = "";
        String tone = "귀여운 키위새";
        if(user != null) {
            Long userId = user.getUser().getId();
            UserInfoVO userInfo = userInfoMapper.getUserInfoById(userId);
            PortfolioVO userPortfolio = portfolioMapper.getPortfolio(userId);
            Long animalId = userInfo.getAnimalId();
            AnimalCharacterVO animalCharacter = animalCharacterMapper.getCharacterById(animalId);
            tone = userInfo.getProfileSummary() + animalCharacter.getAnimalName();
            userData += userInfo.toString();
            userData += userPortfolio.toString();
        }
        text = text.formatted(tone, userData, data1.toVO().toString(), data2.toVO().toString());

        log.info(text);
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
    public List<ProductReviewDTO> getProductReviews(Long id) {
        return productMapper.getProductReviewByProductId(id)
                .stream()
                .map(ProductReviewDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReviewDTO> getMyReviews(Long userId) {
        return productMapper.getProductReviewByUserId(userId)
                .stream()
                .map(ProductReviewDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public Long insertProductReview(ProductReviewDTO productReviewDTO, Long productId, Long userId) {
        ProductReviewVO existingReview = productMapper.getProductReviewByProductIdAndUserId(productId, userId);
        if (existingReview != null) {
            throw new NotFoundException("이미 등록된 리뷰가 있습니다.");
        }

        UserInfoVO userInfo = userInfoMapper.getUserInfoById(userId);
        AnimalCharacterVO animalCharacter = animalCharacterMapper.getCharacterById(userId);

        productReviewDTO.setProductId(productId);
        productReviewDTO.setUserId(userId);
        productReviewDTO.setWriter(userInfo.getProfileSummary() + " " + animalCharacter.getAnimalName());

        ProductReviewVO vo = productReviewDTO.toVO();

        int result = productMapper.insertProductReview(vo);
        if (result == 0) {
            throw new NotFoundException("해당 글을 등록할 수 없습니다.");
        }
        return vo.getId();
    }

    @Override
    public Long deleteProductReview(Long id, Long userId) {
        int result = productMapper.deleteProductReview(id, userId);
        if (result == 0) {
            throw new NotFoundException("해당 글을 삭제할 수 없습니다.");
        }
        return id;
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

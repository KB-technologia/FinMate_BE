package org.finmate.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.common.util.OpenAiApi;
import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    private final OpenAiApi openAiApi;
    //TODO: 서비스 구현


    @Override
    public ProductDTO getProductDetail(Long id) {
        return ProductDTO.from(productMapper.getProductDetail(id));
    }

    @Override
    public ProductComparisonResultDTO compareProducts(Long id1, Long id2) {
        //openAiApi.callResponses();
        return null;
    }
}

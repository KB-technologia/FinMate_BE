package org.finmate.product.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.mapper.ProductMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/recommendation")
@Api(tags = "상품 추천 API")
public class ProductRecommendationController {


    private final ProductMapper productMapper;

    @GetMapping("/random")
    ResponseEntity<List<ProductDTO<?>>> getRandomProductRecommendation(){
        return ResponseEntity.ok(productMapper.getRandomProductRecommendation()
                .stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList()));
    }
}

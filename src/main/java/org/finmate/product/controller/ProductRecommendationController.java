package org.finmate.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.domain.CustomUser;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.mapper.ProductMapper;
import org.finmate.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/recommendation")
@Api(tags = "상품 추천 API")
public class ProductRecommendationController {


    private final ProductMapper productMapper;
    private final ProductService productService;

    /**
     * 랜덤 상품 추천
     * 메인페이지에서 비로그인 사용자에게 보여줄 8개의 랜덤한 상품
     */
    @ApiOperation(value = "랜덤 상품 추천", notes = "메인 화면에서 무작위로 금융 상품 8개를 추천합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 랜덤 추천 상품을 반환했습니다."),
            @ApiResponse(code = 500, message = "서버 오류 발생")
    })
    @GetMapping("/random")
    ResponseEntity<List<ProductDTO<?>>> getRandomProductRecommendation(){
        return ResponseEntity.ok(productMapper.getRandomProductRecommendation()
                .stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList()));
    }

    /**
     * 사용자 맞춤 상품 추천
     * (사용자 성향 + 재무포트폴리오 + 회원가입설문 + 사용자 5대 지표)를 활용하여 상품 추천
     */
    @ApiOperation(value = "사용자 맞춤 상품 추천", notes = "사용자 성향에 맞는 상품을 추천합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 추천 상품을 반환했습니다."),
            @ApiResponse(code = 500, message = "서버 오류 발생")
    })
    @GetMapping("/all")
    ResponseEntity<List<ProductDTO<?>>> getCustomizedProductsRecommendation(@ApiIgnore @AuthenticationPrincipal CustomUser customUser){

        // 사용자 ID 추출
        Long userId = customUser.getUser().getId();

        return ResponseEntity.ok(productService.getCustomizedProducts(userId));
    }
}

package org.finmate.product.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.domain.CustomUser;
import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.dto.ProductReviewDTO;
import org.finmate.product.dto.ReviewIdResponse;
import org.finmate.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/product")
@Log4j2
@Api(tags = "금융 상품 API")
public class ProductController {
    private final ProductService productService;

    @ApiOperation(value = "금융 상품 전체 보기", notes = "전체 금융 상품을 불러오는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO<?>>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @ApiOperation(value = "금융 상품 비교", notes = "금융 상품을 비교하는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductComparisonResultDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping("/compare")
    public ResponseEntity<ProductComparisonResultDTO> compareProducts(
            @ApiParam(value = "상품 ID 1", required = true, example = "1")
            @RequestParam Long id1,
            @ApiParam(value = "상품 ID 2", required = true, example = "2")
            @RequestParam Long id2) {
        return ResponseEntity.ok(productService.compareProducts(id1, id2));
    }


    @ApiOperation(value = "금융 상품 상세 보기", notes = "금융 상품 ID로 상세 정보를 불러오는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO<?>> getProductDetail(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDetail(id));
    }

    @ApiOperation(value = "금융 상품 리뷰 보기", notes = "금융 상품 ID로 금융 상품 리븊 불러오는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductReviewDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping("/{id}/review")
    public ResponseEntity<List<ProductReviewDTO>> getProductReviews(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productService.getProductReviews(id));
    }

    @ApiOperation(value = "금융 상품 리뷰 등록", notes = "금융 상품에 대한 리뷰를 등록하는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ReviewIdResponse.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @PostMapping("/{id}/review")
    public ResponseEntity<ReviewIdResponse> insertProductReview(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable Long id,
            @ApiParam(value = "등록할 금융 상품 리뷰 DTO", required = true)
            @RequestBody ProductReviewDTO review,
            @ApiParam(value = "유저 ID", required = true, example = "1")
            @AuthenticationPrincipal CustomUser user
    ) {
        review.setProductId(id);
        review.setUserId(user.getUser().getId());
        Long resultId = productService.insertProductReview(review);
        return ResponseEntity.ok(new ReviewIdResponse(resultId));
    }

    @ApiOperation(value = "금융 상품 리뷰 삭제", notes = "자신이 등록한 리뷰 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ReviewIdResponse.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @DeleteMapping("/{id}/review")
    public ResponseEntity<ReviewIdResponse> deleteProductReview(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable Long id,
            @ApiParam(value = "유저 ID", required = true, example = "1")
            @AuthenticationPrincipal CustomUser user
    ) {
        return ResponseEntity.ok(new ReviewIdResponse(productService.deleteProductReview(id, user.getUser().getId())));
    }
}

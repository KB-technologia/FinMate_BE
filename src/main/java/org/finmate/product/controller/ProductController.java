package org.finmate.product.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.exception.NotFoundException;
import org.finmate.member.domain.CustomUser;
import org.finmate.product.dto.*;
import org.finmate.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Log4j2
@Api(tags = "금융 상품 API")
public class ProductController {
    private final ProductService productService;

    @ApiOperation(value = "금융 상품 전체 보기", notes = "전체 금융 상품을 불러오는 API")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductDTO.class)
    @GetMapping
    public ResponseEntity<List<ProductDTO<?>>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @ApiOperation(value = "금융 상품 비교", notes = "금융 상품을 비교하는 API")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductComparisonResultDTO.class)
    @GetMapping("/compare")
    public ResponseEntity<ProductComparisonResultDTO> compareProducts(
            @ApiParam(value = "상품 ID 1", required = true, example = "1")
            @RequestParam final Long id1,
            @ApiParam(value = "상품 ID 2", required = true, example = "2")
            @RequestParam final Long id2,
            @ApiIgnore @AuthenticationPrincipal CustomUser user) {
        return ResponseEntity.ok(productService.compareProducts(id1, id2, user));
    }


    @ApiOperation(value = "금융 상품 상세 보기", notes = "금융 상품 ID로 상세 정보를 불러오는 API")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO<?>> getProductDetail(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable final Long id) {
        return ResponseEntity.ok(productService.getProductDetail(id));
    }

    @ApiOperation(value = "금융 상품 리뷰 보기", notes = "금융 상품 ID로 금융 상품 리븊 불러오는 API")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductReviewDTO.class)
    @GetMapping("/{id}/review")
    public ResponseEntity<List<ProductReviewDTO>> getProductReviews(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productService.getProductReviews(id));
    }

    @ApiOperation(value = "금융 상품 리뷰 등록", notes = "금융 상품에 대한 리뷰를 등록하는 API")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ReviewIdResponse.class)
    @PostMapping("/{id}/review")
    public ResponseEntity<ReviewIdResponse> insertProductReview(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable final Long id,
            @ApiParam(value = "등록할 금융 상품 리뷰 DTO", required = true)
            @RequestBody final ProductReviewDTO review,
            @ApiIgnore @AuthenticationPrincipal CustomUser user
    ) {
        Long resultId = productService.insertProductReview(review, id, user.getUser().getId());
        return ResponseEntity.ok(new ReviewIdResponse(resultId));
    }

    @ApiOperation(value = "금융 상품 리뷰 삭제", notes = "자신이 등록한 리뷰 삭제 API")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ReviewIdResponse.class)
    @DeleteMapping("/{id}/review")
    public ResponseEntity<ReviewIdResponse> deleteProductReview(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable final Long id,
            @ApiIgnore @AuthenticationPrincipal final CustomUser user
    ) {
        return ResponseEntity.ok(new ReviewIdResponse(productService.deleteProductReview(id, user.getUser().getId())));
    }


    @ApiOperation(value = "상품 필터링 조회", notes = "상품 타입, 은행, 검색어 등으로 필터링하여 상품을 조회하는 API")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductDTO.class, responseContainer = "List")
    @GetMapping("/filter")
    public ResponseEntity<List<ProductDTO<?>>> getFilteredProducts(
            @ApiParam(value = "검색 키워드 (상품명 또는 은행명)", required = false, example = "글로벌")
            @RequestParam(required = false) final String query,

            @ApiParam(value = "상품 타입", required = false, example = "DEPOSIT", allowableValues = "DEPOSIT,SAVINGS,FUND")
            @RequestParam(required = false) final List<String> productType,

            @ApiParam(value = "은행명 목록 (복수 선택 가능)", required = false, example = "국민")
            @RequestParam(required = false) final List<String> bankName,

            @ApiParam(value = "펀드 종류 목록 (펀드일 때만)", required = false, example = "ETF")
            @RequestParam(required = false) final List<String> fundType,

            @ApiParam(value = "정렬 순서", required = false, example = "desc", allowableValues = "asc,desc")
            @RequestParam(defaultValue = "desc") final String sortType,

            @ApiIgnore @AuthenticationPrincipal CustomUser user)
    {


        // 필터 DTO 구성
        ProductFilterDTO filter = new ProductFilterDTO();
        filter.setQuery(query);
        filter.setProductType(productType);
        filter.setBankName(bankName);
        filter.setFundType(fundType);
        filter.setSortType(sortType);

        // 필터링 서비스 호출
        List<ProductDTO<?>> result = productService.getFilteredProducts(filter, user);

        if (result == null || result.isEmpty()) {
            throw new NotFoundException("조건에 맞는 상품을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(result);
    }
}

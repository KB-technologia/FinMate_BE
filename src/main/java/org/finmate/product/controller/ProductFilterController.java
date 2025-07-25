package org.finmate.product.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.exception.NotFoundException;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.dto.ProductFilterDTO;
import org.finmate.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/filter")
@CrossOrigin(origins = "http://localhost:5173")
@Log4j2
@Api(tags = "금융 상품 필터링 API")
public class ProductFilterController {
    private final ProductService productService;

    @ApiOperation(value = "상품 필터링 조회", notes = "상품 타입, 은행, 검색어 등으로 필터링하여 상품을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductDTO.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping()
    public ResponseEntity<List<ProductDTO<?>>> getFilteredProducts(
            @ApiParam(value = "검색 키워드 (상품명 또는 은행명)", required = false, example = "글로벌")
            @RequestParam(required = false) String query,

            @ApiParam(value = "상품 타입", required = false, example = "DEPOSIT", allowableValues = "DEPOSIT,SAVINGS,FUND")
            @RequestParam(required = false) String productType,

            @ApiParam(value = "은행명 목록 (복수 선택 가능)", required = false, example = "국민")
            @RequestParam(required = false) List<String> bankName,

            @ApiParam(value = "펀드 종류 목록 (펀드일 때만)", required = false, example = "ETF")
            @RequestParam(required = false) List<String> fundType,

            @ApiParam(value = "정렬 순서", required = false, example = "desc", allowableValues = "asc,desc")
            @RequestParam(defaultValue = "desc") String sortOrder) {


        // 필터 DTO 구성
        ProductFilterDTO filter = new ProductFilterDTO();
        filter.setQuery(query);
        filter.setProductType(productType);
        filter.setBankName(bankName);
        filter.setFundType(fundType);
        filter.setSortOrder(sortOrder);

        // 필터링 서비스 호출
        List<ProductDTO<?>> result = productService.getFilteredProducts(filter);

        if (result == null || result.isEmpty()) {
            throw new NotFoundException("조건에 맞는 상품을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(result);
    }
}
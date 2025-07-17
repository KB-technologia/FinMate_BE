package org.finmate.product.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Log4j2
@Api(tags = "금융 상품 API")
public class ProductController {
    private final ProductService productService;

    //TODO: 전체 상품 API 구현

    @ApiOperation(value = "금융 상품 상세 보기", notes = "금융 상품 ID로 상세 정보를 불러오는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductDetail(
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDetail(id));
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
        //TODO: 로직 작성

        return ResponseEntity.ok().body(new ProductComparisonResultDTO());
    }
}

package org.finmate.product.controller;

import lombok.RequiredArgsConstructor;
import org.finmate.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    //TODO: 전체 상품 API 구현

//    @GetMapping("/{id}")
//    public ResponseEntity<T> getProductDetail(@PathVariable Long id) {
//        return ResponseEntity.ok();
//    }
}

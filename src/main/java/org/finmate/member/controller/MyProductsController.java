package org.finmate.member.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Api(tags = "가입 상품 API")
public class MyProductsController {

    @GetMapping("/myproducts")
    public ResponseEntity<Resource> getMyProductsList(){
        Resource res = new ClassPathResource("dummy/mydata/user_financial_products.json");
        return ResponseEntity.ok(res); // JSON 그대로 전송
    }
}

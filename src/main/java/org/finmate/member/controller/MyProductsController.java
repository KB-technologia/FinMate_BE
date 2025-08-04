package org.finmate.member.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.finmate.adapter.mydata.MyDataApi;
import org.finmate.adapter.mydata.dto.MyDataResponseDTO;
import org.finmate.member.domain.CustomUser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Api(tags = "가입 상품 API")
public class MyProductsController {

    private final MyDataApi myDataApi;

    @GetMapping("/myproducts")
    public ResponseEntity<MyDataResponseDTO> getMyProductsList(
            @ApiIgnore @AuthenticationPrincipal CustomUser customUser
    ){
        Long userId = customUser.getUser().getId();
        return ResponseEntity.ok(myDataApi.getMyData(userId)); // JSON 그대로 전송
    }
}

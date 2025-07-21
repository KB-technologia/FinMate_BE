package org.finmate.product.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.product.dto.ProductDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/favorite")
@Api(tags = "즐겨찾기 API")
public class FavoriteController {


    @GetMapping("{userId}")
    public List<ProductDTO<?>> getFavoriteList(@PathVariable Long userId){
        return List.of();
    }
}

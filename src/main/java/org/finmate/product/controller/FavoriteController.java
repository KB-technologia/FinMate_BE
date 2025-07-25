package org.finmate.product.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.domain.CustomUser;
import org.finmate.product.dto.FavoriteDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/favorite")
@Api(tags = "즐겨찾기 API")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @ApiOperation(value = "즐겨찾기 조회", notes = "사용자가 즐겨찾기한 상품들 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = FavoriteDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getFavoriteList(
            @AuthenticationPrincipal CustomUser user) {

        Long userId = user.getUser().getId();
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }

    @ApiOperation(value = "즐겨찾기 등록", notes = "사용자가 특정 금융상품을 즐겨찾기에 등록합니다.")
    @PostMapping("/{productId}")
    public ResponseEntity<Void> enrollFavorite(
            @AuthenticationPrincipal CustomUser user,
            @ApiParam(value = "상품 ID", required = true, example = "1")
            @PathVariable Long productId) {

        Long userId = user.getUser().getId();
        favoriteService.enrollFavorite(userId, productId);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "즐겨찾기 삭제", notes = "즐겨찾기 ID로 등록된 항목을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "즐겨찾기 삭제 성공"),
            @ApiResponse(code = 404, message = "즐겨찾기 항목을 찾을 수 없음")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteFavorite(
            @AuthenticationPrincipal CustomUser user,
            @ApiParam(value = "즐겨찾기 ID", required = true, example = "1")
            @PathVariable Long productId
    ) {
        Long userId = user.getUser().getId();
        favoriteService.deleteFavorite(userId, productId);
        return ResponseEntity.noContent().build();
    }
}

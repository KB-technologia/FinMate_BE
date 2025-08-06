package org.finmate.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.dto.MyPageResponseDTO;
import org.finmate.member.dto.MyPageUpdateRequestDTO;
import org.finmate.member.dto.UserStatResponseDTO;
import org.finmate.member.service.MemberService;
import org.finmate.member.service.MyPageService;
import org.finmate.product.dto.ProductReviewDTO;
import org.finmate.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Api(tags = "마이페이지 API")
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberService memberService;
    private final ProductService productService;

    @ApiOperation(value = "마이페이지 조회", notes = "로그인한 사용자의 마이페이지 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공", response = MyPageResponseDTO.class),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "접근 권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/me")
    public ResponseEntity<MyPageResponseDTO> getMyPage(@ApiIgnore @AuthenticationPrincipal CustomUser user) {
        Long userId = user.getUser().getId();
        return ResponseEntity.ok(myPageService.getMyPageInfo(userId));
    }

    @ApiOperation(value = "사용자 스탯 조회", notes = "로그인한 사용자의 스탯(5대 지표) 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공", response = UserStatResponseDTO.class),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "접근 권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/stat")
    public ResponseEntity<UserStatResponseDTO> getMyStat(@ApiIgnore @AuthenticationPrincipal CustomUser user) {
        Long userId = user.getUser().getId();
        return ResponseEntity.ok(memberService.getStatByUserId(userId));
    }

    @ApiOperation(
            value = "마이페이지 수정",
            notes = "비밀번호, 이메일(인증 필요), 생년월일 중 원하는 항목만 선택적으로 수정할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "인증 필요"),
            @ApiResponse(code = 403, message = "접근 권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PatchMapping("/me")
    public ResponseEntity<Void> updateMyPage(
            @ApiIgnore @AuthenticationPrincipal CustomUser user,
            @ApiParam(value = "수정할 마이페이지 정보 (수정 항목만 포함)", required = true)
            @RequestBody MyPageUpdateRequestDTO dto
    ) {
        Long userId = user.getUser().getId();
        myPageService.updateMyPageInfo(userId, dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "회원 탈퇴", notes = "사용자의 계정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "탈퇴 성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "접근 권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> withdraw(@ApiIgnore @AuthenticationPrincipal CustomUser user) {
        Long userId = user.getUser().getId();
        memberService.withdraw(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "내가 작성한 리뷰 목록 조회", notes = "로그인한 사용자가 작성한 금융 상품 리뷰들을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "접근 권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/review")
    public ResponseEntity<List<ProductReviewDTO>> getMyReviews(@ApiIgnore @AuthenticationPrincipal CustomUser user) {
        Long userId = user.getUser().getId();
        List<ProductReviewDTO> reviews = productService.getMyReviews(userId);
        return ResponseEntity.ok(reviews);
    }
}

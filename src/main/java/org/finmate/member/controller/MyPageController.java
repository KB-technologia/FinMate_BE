package org.finmate.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDTO;
import org.finmate.member.dto.MyPageUpdateRequestDTO;
import org.finmate.member.service.MemberService;
import org.finmate.member.service.MyPageService;
import org.finmate.product.dto.ProductReviewDTO;
import org.finmate.product.service.ProductService;
import org.finmate.member.domain.CustomUser;
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
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/me")
    public MyPageResponseDTO getMyPage(@ApiIgnore @AuthenticationPrincipal CustomUser user) {
        Long userId = user.getUser().getId();
        return myPageService.getMyPageInfo(userId);
    }

    @ApiOperation(value = "마이페이지 수정", notes = "비밀번호, 이메일, 생년월일 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PatchMapping("/me")
    public void updateMyPage(@ApiIgnore @AuthenticationPrincipal CustomUser user, @RequestBody MyPageUpdateRequestDTO dto) {
        Long userId = user.getUser().getId();
        myPageService.updateMyPageInfo(userId, dto);
    }

    @DeleteMapping(value = "/withdraw", produces = "text/plain; charset=UTF-8")
    @ApiOperation(value = "회원 탈퇴", notes = "사용자의 계정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "탈퇴 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> withdraw(@ApiIgnore @AuthenticationPrincipal CustomUser user) {
        String accountId = user.getUser().getAccountId();
        memberService.withdraw(accountId);
        return ResponseEntity.ok("회원 탈퇴 완료");
    }

    @ApiOperation(value = "내가 작성한 리뷰 목록 조회", notes = "로그인한 사용자가 작성한 금융 상품 리뷰들을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/review")
    public ResponseEntity<List<ProductReviewDTO>> getMyReviews(@ApiIgnore @AuthenticationPrincipal CustomUser user) {
        Long userId = user.getUser().getId();
        List<ProductReviewDTO> reviews = productService.getMyReviews(userId);
        return ResponseEntity.ok(reviews);
    }
}

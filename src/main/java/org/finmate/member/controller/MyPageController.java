package org.finmate.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDto;
import org.finmate.member.dto.MyPageUpdateRequestDto;
import org.finmate.member.service.MemberService;
import org.finmate.member.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Api(tags = "마이페이지 API")
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberService memberService;

    @ApiOperation(value = "마이페이지 조회", notes = "로그인한 사용자의 마이페이지 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공", response = MyPageResponseDto.class),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/me")
    public MyPageResponseDto getMyPage() {
        Long userId = 1L; // TODO: 인증 처리 후 교체
        return myPageService.getMyPageInfo(userId);
    }

    @ApiOperation(value = "마이페이지 수정", notes = "비밀번호, 이메일, 생년월일 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PatchMapping("/me")
    public void updateMyPage(@RequestBody MyPageUpdateRequestDto dto) {
        Long userId = 1L; // TODO: 인증 처리 후 교체
        myPageService.updateMyPageInfo(userId, dto);
    }

    @DeleteMapping("/withdraw")
    @ApiOperation(value = "회원 탈퇴", notes = "사용자의 계정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "탈퇴 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> withdraw(HttpSession session) {
        String accountId = (String) session.getAttribute("accountId");
        memberService.withdraw(accountId);
        session.invalidate(); // 세션 무효화

        return ResponseEntity.ok("회원 탈퇴 완료");
    }
}

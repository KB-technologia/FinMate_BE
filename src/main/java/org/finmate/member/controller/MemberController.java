package org.finmate.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.dto.*;
import org.finmate.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Api(tags = "아이디 찾기 / 비밀번호 변경 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/find-accountid")
    @ApiOperation(value = "아이디 찾기", notes = "이메일 인증이 완료된 UUID로 등록된 accountId를 반환")
    public FindAccountIdResponseDTO findAccountId(@RequestBody FindAccountIdRequestDTO dto) {
        return memberService.findAccountIdByUuid(dto.getUuid());
    }

    @PostMapping("/change-password/verify")
    @ApiOperation(value = "비밀번호 변경 전 사용자 검증", notes = "uuid 인증 여부 + accountId 매칭 확인")
    public ResponseEntity<Void> verifyUser(@RequestBody VerifyUserRequestDTO dto) {
        boolean valid = memberService.verifyUser(dto.getUuid(), dto.getAccountId());
        return valid ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/change-password")
    @ApiOperation(value = "비밀번호 변경")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestDTO dto) {
        memberService.resetPassword(dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "회원가입", notes = "회원 정보를 입력받아 회원가입을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회원가입 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping(value = "/join")
    public ResponseEntity<Void> join(@RequestBody SignupRequestDTO dto) {
        memberService.signup(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-id")
    @ResponseBody
    @ApiOperation(value = "아이디 중복 확인", notes = "accountId가 이미 존재하는지 확인")
    @ApiParam(value = "중복 확인할 사용자 계정 ID", required = true)
    public ResponseEntity<Boolean> checkAccountId(@RequestParam("accountId") String accountId) {
        boolean isDuplicate = memberService.isAccountIdDuplicate(accountId);
        return ResponseEntity.ok(isDuplicate);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "사용자의 계정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "탈퇴 성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "접근 권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping
    public ResponseEntity<Void> withdraw(@ApiIgnore @AuthenticationPrincipal CustomUser user) {
        Long userId = user.getUser().getId();
        memberService.withdraw(userId);
        return ResponseEntity.ok().build();
    }
}


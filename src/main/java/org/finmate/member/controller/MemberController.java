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
@Api(tags = "사용자 관리 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/find-accountid")
    @ApiOperation(value = "아이디 찾기", notes = "이메일 인증이 완료된 UUID로 등록된 accountId를 반환")
    @ApiResponse(code = 200, message = "정상적으로 사용자 ID가 반환되었습니다.", response = FindAccountIdResponseDTO.class)
    public ResponseEntity<FindAccountIdResponseDTO> findAccountId(
            @RequestBody final FindAccountIdRequestDTO dto
    ) {
        return ResponseEntity.ok(memberService.findAccountIdByUuid(dto.getUuid()));
    }

    @PostMapping("/change-password/verify")
    @ApiOperation(value = "비밀번호 변경 전 사용자 검증", notes = "uuid 인증 여부 + accountId 매칭 확인")
    @ApiResponse(code = 200, message = "정상적으로 사용자가 검증되었습니다.")
    public ResponseEntity<Void> verifyUser(
            @RequestBody final VerifyUserRequestDTO dto
    ) {
        memberService.verifyUser(dto.getUuid(), dto.getAccountId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    @ApiOperation(value = "비밀번호 변경")
    @ApiResponse(code = 200, message = "정상적으로 사용자 비밀번호가 변경되었습니다.")
    public ResponseEntity<Void> changePassword(
            @RequestBody final ChangePasswordRequestDTO dto
    ) {
        memberService.resetPassword(dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "회원가입", notes = "회원 정보를 입력받아 회원가입을 수행합니다.")
    @ApiResponse(code = 200, message = "회원가입 성공")
    @PostMapping(value = "/join")
    public ResponseEntity<Void> join(@RequestBody final SignupRequestDTO dto) {
        memberService.signup(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-id")
    @ResponseBody
    @ApiOperation(value = "아이디 중복 확인", notes = "accountId가 이미 존재하는지 확인")
    @ApiResponse(code = 200, message = "중복 확인 성공")
    public ResponseEntity<Boolean> checkAccountId(
            @ApiParam(value = "중복 확인할 사용자 계정 ID", required = true)
            @RequestParam("accountId") final String accountId
    ) {
        boolean isDuplicate = memberService.isAccountIdDuplicate(accountId);
        return ResponseEntity.ok(isDuplicate);
    }

    @ApiOperation(value = "회원 탈퇴", notes = "사용자의 계정을 삭제합니다.")
    @ApiResponse(code = 200, message = "탈퇴 성공")
    @DeleteMapping
    public ResponseEntity<Void> withdraw(
            @ApiIgnore @AuthenticationPrincipal final CustomUser user
    ) {
        Long userId = user.getUser().getId();
        memberService.withdraw(userId);
        return ResponseEntity.ok().build();
    }
}


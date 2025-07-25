package org.finmate.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.ChangePasswordRequestDTO;
import org.finmate.member.dto.FindAccountIdRequestDTO;
import org.finmate.member.dto.FindAccountIdResponseDTO;
import org.finmate.member.dto.VerifyUserRequestDTO;
import org.finmate.member.service.FindService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "아이디 찾기 / 비밀번호 변경 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class FindController {

    private final FindService findService;

    @PostMapping("/findaccountid")
    @ApiOperation(value = "아이디 찾기", notes = "이메일 인증이 완료된 UUID로 등록된 accountId를 반환")
    public FindAccountIdResponseDTO findAccountId(@RequestBody FindAccountIdRequestDTO dto) {
        String accountId = findService.findAccountIdByUuid(dto.getUuid());
        return new FindAccountIdResponseDTO(accountId);
    }

    @PostMapping("/changepassword/verify")
    @ApiOperation(value = "비밀번호 변경 전 사용자 검증", notes = "uuid 인증 여부 + accountId 매칭 확인")
    public ResponseEntity<Void> verifyUser(@RequestBody VerifyUserRequestDTO dto) {
        boolean valid = findService.verifyUser(dto.getUuid(), dto.getAccountId());
        return valid ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/changepassword")
    @ApiOperation(value = "비밀번호 변경")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestDTO dto) {
        findService.resetPassword(dto);
        return ResponseEntity.ok().build();
    }
}


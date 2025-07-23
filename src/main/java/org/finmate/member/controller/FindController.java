package org.finmate.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.member.service.FindService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "아이디 찾기 / 비밀번호 변경 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class FindController {

    private final FindService findService;

    @GetMapping("/findaccountid")
    @ApiOperation(value = "아이디 찾기", notes = "이메일 인증이 완료된 UUID로 등록된 accountId를 반환")
    public ResponseEntity<String> findAccountId(@RequestParam String uuid) {
        String accountId = findService.findAccountIdByUuid(uuid);
        return accountId != null
                ? ResponseEntity.ok(accountId)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증이 완료되지 않았거나 사용자 없음");
    }

    // 2. 비밀번호 재설정 가능 여부 확인
    @PostMapping("/changepassword/verify")
    @ApiOperation(value = "비밀번호 변경 전 사용자 검증", notes = "uuid 인증 여부 + accountId 매칭 확인")
    public ResponseEntity<String> verifyUser(@RequestParam String uuid, @RequestParam String accountId) {
        boolean valid = findService.verifyUser(uuid, accountId);
        return valid
                ? ResponseEntity.ok("확인 완료")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("정보 불일치");
    }

    // 3. 비밀번호 재설정
    @PostMapping("/changepassword")
    @ApiOperation(value = "비밀번호 재설정", notes = "비밀번호를 새로 저장")
    public ResponseEntity<String> resetPassword(@RequestParam String accountId,
                                                @RequestParam String newPassword) {
        boolean success = findService.resetPassword(accountId, newPassword);
        return success
                ? ResponseEntity.ok("비밀번호 변경 완료")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 없음");
    }
}


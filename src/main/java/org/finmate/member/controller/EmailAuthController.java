package org.finmate.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.EmailRequestDTO;
import org.finmate.member.dto.EmailVerifyRequestDTO;
import org.finmate.member.service.EmailAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "이메일 인증 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @ApiOperation(value = "이메일 인증 요청", notes = "사용자의 이메일로 인증 코드를 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증코드 발송 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping(value = "/emailauthentication", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> sendAuthCode(
            @RequestBody EmailRequestDTO request
    ) {
        String uuid = emailAuthService.sendAuthCode(request.getEmail());
        return ResponseEntity.ok(uuid); // uuid 반환 or "인증코드 전송 성공" 메시지로 변경 가능
    }

    @ApiOperation(value = "이메일 인증 코드 검증", notes = "UUID와 인증 코드를 통해 이메일 인증을 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증 성공 여부 반환"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping(value = "/emailauthentication/verify", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Boolean> verifyAuthCode(
            @RequestBody EmailVerifyRequestDTO request
    ) {
        boolean result = emailAuthService.verifyCode(request.getRequestId(), request.getAuthCode());
        return ResponseEntity.ok(result);
    }
}

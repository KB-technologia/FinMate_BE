package org.finmate.email.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.email.dto.EmailAuthInitResponseDTO;
import org.finmate.email.dto.EmailRequestDTO;
import org.finmate.email.dto.EmailVerifyRequestDTO;
import org.finmate.email.service.EmailAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
@Api(tags = "이메일 인증 API")
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @ApiOperation(value = "이메일 인증 요청", notes = "사용자의 이메일로 인증 코드를 전송합니다.")
    @ApiResponse(code = 200, message = "인증코드 발송 성공", response = EmailAuthInitResponseDTO.class)
    @PostMapping("/authentication")
    public ResponseEntity<EmailAuthInitResponseDTO> sendAuthCode(
            @RequestBody final EmailRequestDTO request
    ) {
        String uuid = emailAuthService.sendAuthCode(request.getEmail());
        EmailAuthInitResponseDTO response = new EmailAuthInitResponseDTO(uuid);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(
            value = "회원가입용 이메일 인증 요청",
            notes = "회원가입 시 이메일 중복 확인과 함께 인증 코드를 전송합니다. 이미 가입된 이메일인 경우 오류를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "인증코드 발송 성공", response = EmailAuthInitResponseDTO.class),
            @ApiResponse(code = 400, message = "이미 가입된 이메일", response = Map.class)
    })
    @PostMapping("/authentication/signup")
    public ResponseEntity<?> sendAuthCodeForSignUp(
            @RequestBody final EmailRequestDTO request
    ) {
        try {
            String uuid = emailAuthService.sendAuthCodeForSignUp(request.getEmail());
            EmailAuthInitResponseDTO response = new EmailAuthInitResponseDTO(uuid);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @ApiOperation(value = "이메일 인증 코드 검증", notes = "UUID와 인증 코드를 통해 이메일 인증을 검증합니다.")
    @ApiResponse(code = 200, message = "인증 성공 여부 반환", response = Boolean.class)
    @PostMapping("/authentication/verify")
    public ResponseEntity<Boolean> verifyAuthCode(
            @RequestBody final EmailVerifyRequestDTO request
    ) {
        boolean result = emailAuthService.verifyCode(request.getRequestId(), request.getAuthCode());
        return ResponseEntity.ok(result);
    }
}
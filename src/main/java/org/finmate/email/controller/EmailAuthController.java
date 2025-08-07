package org.finmate.email.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.email.dto.EmailAuthInitResponseDTO;
import org.finmate.email.dto.EmailRequestDTO;
import org.finmate.email.dto.EmailVerifyRequestDTO;
import org.finmate.email.service.EmailAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
@Api(tags = "이메일 인증 API")
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @ApiOperation(value = "이메일 인증 요청", notes = "사용자의 이메일로 인증 코드를 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증코드 발송 성공", response = EmailAuthInitResponseDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 404, message = "리소스를 찾을 수 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping( "/authentication")
    public ResponseEntity<EmailAuthInitResponseDTO> sendAuthCode(
            @RequestBody final EmailRequestDTO request
    ) {
        String uuid = emailAuthService.sendAuthCode(request.getEmail());
        EmailAuthInitResponseDTO response = new EmailAuthInitResponseDTO(uuid);
        return ResponseEntity.ok(response); //
    }

    @ApiOperation(value = "이메일 인증 코드 검증", notes = "UUID와 인증 코드를 통해 이메일 인증을 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증 성공 여부 반환", response = Boolean.class),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "권한 없음"),
            @ApiResponse(code = 404, message = "리소스를 찾을 수 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping( "/authentication/verify")
    public ResponseEntity<Boolean> verifyAuthCode(
            @RequestBody final EmailVerifyRequestDTO request
    ) {
        boolean result = emailAuthService.verifyCode(request.getRequestId(), request.getAuthCode());
        return ResponseEntity.ok(result);
    }
}

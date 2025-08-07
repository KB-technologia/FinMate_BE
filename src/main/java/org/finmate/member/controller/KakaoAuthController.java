package org.finmate.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.domain.KakaoUser;
import org.finmate.member.domain.UserVO;
import org.finmate.member.service.KakaoService;
import org.finmate.security.dto.AuthResultDTO;
import org.finmate.security.util.JwtProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
@Api(tags = "카카오 로그인 API")
public class KakaoAuthController {

    private final KakaoService kakaoService;
    // 인가코드 받아서 로그인 처리
    @ApiOperation(value = "카카오 로그인 콜백", notes = "인가 코드를 받아 카카오 로그인 처리 및 JWT 발급")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "정상적으로 콜백이 처리되었습니다.", response = AuthResultDTO.class)
    })
    @GetMapping("/callback")
    public ResponseEntity<AuthResultDTO> kakaoCallback(
            @ApiParam(value = "카카오 인가 코드", required = true)
            @RequestParam final String code
    ) {
        // 1. 인가코드로 액세스 토큰 요청
        String accessToken = kakaoService.getAccessToken(code);

        // 2. 토큰으로 카카오 사용자 정보 요청
        KakaoUser kakoUser = kakaoService.getUserInfo(accessToken);

        // 3. 기존 유저 조회 또는 DB에 저장 -> isNewUser 플래그 전달을 위해서 JSON 응답으로 변경
        AuthResultDTO result = kakaoService.getOrCreateUserAndBuildAuthDTO(kakoUser);

        // 4. JSON 응답 반환
        return ResponseEntity.ok(result);
    }
}

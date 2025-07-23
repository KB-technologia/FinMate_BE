package org.finmate.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.domain.KakaoUser;
import org.finmate.member.domain.UserVO;
import org.finmate.member.service.KakaoService;
import org.finmate.security.util.JwtProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
@Api(tags = "카카오 로그인 API")
public class KakaoAuthController {

    private final KakaoService kakaoService;
    private final JwtProcessor jwtProcessor;

    // 인가코드 받아서 로그인 처리
    @ApiOperation(value = "카카오 로그인 콜백", notes = "인가 코드를 받아 카카오 로그인 처리 및 JWT 발급")
    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(
            @ApiParam(value = "카카오 인가 코드", required = true)
            @RequestParam String code
    ) {
        // 1. 인가코드로 액세스 토큰 요청
        String accessToken = kakaoService.getAccessToken(code);

        // 2. 토큰으로 카카오 사용자 정보 요청
        KakaoUser kakoUser = kakaoService.getUserInfo(accessToken);

        // 3. 기존 유저 조회 또는 DB에 저장
        UserVO user = kakaoService.getOrCreateUser(kakoUser);

        log.info("JwtProcessor 호출 전 - accountId: {} ", user.getAccountId());

        // 4. JWT 발급
        String jwt = jwtProcessor.generateToken(user.getAccountId());

        // 5. JWT 반환
        Map<String, Object> resposnse = Map.of(
                "token", jwt,
                "accountId", user.getAccountId(),
                "name", user.getName()
        );

        return ResponseEntity.ok(resposnse);
    }
}

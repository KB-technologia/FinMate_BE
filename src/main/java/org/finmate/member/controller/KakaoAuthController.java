package org.finmate.member.controller;

import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
public class KakaoAuthController {

    private final KakaoService kakaoService;
    private final JwtProcessor jwtProcessor;

    // 인가코드 받아서 로그인 처리
    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
        // 1. 인가코드로 액세스 토큰 요청
        String accessToken = kakaoService.getAccessToken(code);

        // 2. 토큰으로 카카오 사용자 정보 요청
        KakaoUser kakoUser = kakaoService.getUserInfo(accessToken);

        // 3. 기존 유저 조회 또는 DB에 저장
        UserVO user = kakaoService.getOrCreateUser(kakoUser);

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

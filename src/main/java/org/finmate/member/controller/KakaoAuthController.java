package org.finmate.member.controller;

import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.KakaoUser;
import org.finmate.member.domain.UserVO;
import org.finmate.member.service.KakaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
public class KakaoAuthController {

    private final KakaoService kakaoService;

    // 1. 인가코드 받아서 로그인 처리
    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
        //인가코드로 토큰 받기
        String accessToken = kakaoService.getAccessToken(code);

        //토큰으로 사용자 정보 요청
        KakaoUser kakoUser = kakaoService.getUserInfo(accessToken);

        //기존 유저 조회 or db에 저장
        UserVO user = kakaoService.getOrCreateUser(kakoUser);

        //(임시응답) 사용자 정보 확인용
        return ResponseEntity.ok(user);
    }
}

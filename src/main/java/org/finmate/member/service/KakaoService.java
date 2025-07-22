package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final String CLIENT_ID = "카카오 REST API 키";
    private final String REDIRECT_URI = "http://localhost:8080/auth/kakao/callback";
}

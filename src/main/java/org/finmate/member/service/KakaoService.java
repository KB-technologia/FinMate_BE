package org.finmate.member.service;

import org.finmate.member.domain.KakaoUser;
import org.finmate.security.dto.AuthResultDTO;

public interface KakaoService {


    String getAccessToken(String code);

    // 2. access token으로 사용자 정보 요청
    KakaoUser getUserInfo(String accessToken);

    AuthResultDTO getOrCreateUserAndBuildAuthDTO(KakaoUser kakaoUser);
}

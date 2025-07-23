package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.KakaoUser;
import org.finmate.member.domain.UserVO;
import org.finmate.member.domain.enums.Provider;
import org.finmate.member.mapper.UserMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.client-id}")
    private String CLIENT_ID;

    @Value("${kakao.redirect-uri}")
    private String REDIRECT_URI;

    private final UserMapper userMapper;

    // 1. 인가 코드로 access token 받기
    public String getAccessToken(String code){
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println("Kakao token response : " + response.getBody());

        JSONObject json = new JSONObject(response.getBody());

        if(!json.has("access_token")) {
            throw new IllegalArgumentException("Kakao 응답에 access_token이 없습니다 : " + response.getBody());
        }

        return json.getString("access_token");
    }

    // 2. access token으로 사용자 정보 요청
    public KakaoUser getUserInfo(String accessToken){
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, request, String.class
        );

        JSONObject json = new JSONObject(response.getBody());
        JSONObject kakaoAccount = json.getJSONObject("kakao_account");
        JSONObject properties = json.getJSONObject("properties");

        String id = String.valueOf(json.getLong("id"));
        String nickname = properties.optString("nickname", "카카오사용자");
        String email = kakaoAccount.optString("email", "no-email@kakao.com");

        System.out.println("getUserInfo - id: " + id + ", nickname: " + nickname + ", email: " + email);

        return new KakaoUser(id, nickname, email);
    }

    // 3. 우리 서비스의 사용자로 DB에 저장 or 조회
    public UserVO getOrCreateUser(KakaoUser kakaoUser){
        String accountId = "kakao_" + kakaoUser.getId();

        UserVO existingUser = userMapper.selectByAccountId(accountId);
        if(existingUser != null){
            System.out.println("기존 사용자: " + existingUser);
            return existingUser;
        }

        UserVO newUser = UserVO.builder()
                .accountId(accountId)
                .email(kakaoUser.getEmail())
                .name(kakaoUser.getNickname())
                .password("kakao")
                .provider(Provider.KAKAO)
                .build();

        System.out.println("새 사용자 생성: " + newUser);

        userMapper.insertUser(newUser);
        return newUser;
    }

}

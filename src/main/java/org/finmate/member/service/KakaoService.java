package org.finmate.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.domain.KakaoUser;
import org.finmate.member.domain.UserVO;
import org.finmate.member.domain.enums.Provider;
import org.finmate.member.dto.KakaoTokenResponse;
import org.finmate.member.dto.KakaoUserResponse;
import org.finmate.member.dto.UserInfoDTO;
import org.finmate.member.mapper.UserMapper;
import org.finmate.security.dto.AuthResultDTO;
import org.finmate.security.dto.UserLoginInfoDTO;
import org.finmate.security.util.JwtProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Log4j2
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final JwtProcessor jwtProcessor;
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

        log.info("Kakao token response : {} ", response.getBody());

        ObjectMapper om = new ObjectMapper();
        try {
            KakaoTokenResponse tokenResponse = om.readValue(response.getBody(), KakaoTokenResponse.class);
            log.info("Kakao token response: {}", tokenResponse);
            return tokenResponse.getAccess_token();
        } catch (Exception e) {
            throw new RuntimeException("카카오 토큰 파싱 실패: " + response.getBody(), e);
        }
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

        ObjectMapper om = new ObjectMapper();

        try{
            // JSON -> DTO 자동 매핑
            KakaoUserResponse kakaoResponse = om.readValue(response.getBody(), KakaoUserResponse.class);
            String id = String.valueOf(kakaoResponse.getId());
            String nickname = kakaoResponse.getProperties().getNickname();
            String email = kakaoResponse.getKakao_account().getEmail();
            log.info("getUserInfo - id: {}, nickname: {}, email: {}", id, nickname, email);

            return new KakaoUser(id,
                    nickname != null ? nickname : "카카오사용자",
                    email != null ? email : "no-email@kakao.com");
        }
        catch (Exception e) {
            log.error("카카오 사용자 정보 파싱 실패: {}", e.getMessage());
            throw new RuntimeException("카카오 사용자 정보 파싱 실패", e);
        }
    }


    public AuthResultDTO getOrCreateUserAndBuildAuthDTO(KakaoUser kakaoUser){
        String accountId = "kakao_" + kakaoUser.getId();
        UserVO user = userMapper.selectByAccountId(accountId);

        boolean isNewUser = false;
        if(user == null){
            user = UserVO.builder()
                    .accountId(accountId)
                    .email(kakaoUser.getEmail())
                    .name(kakaoUser.getNickname())
                    .password("kakao")
                    .provider(Provider.KAKAO)
                    .build();
            userMapper.insertUser(user);
            isNewUser = true;
        }

        String token = jwtProcessor.generateToken(user.getAccountId());
        UserLoginInfoDTO userLoginInfoDTO = UserLoginInfoDTO.of(user);

        return new AuthResultDTO(token, userLoginInfoDTO, isNewUser);
    }


//    // 3. 우리 서비스의 사용자로 DB에 저장 or 조회
//    public UserVO getOrCreateUser(KakaoUser kakaoUser){
//        String accountId = "kakao_" + kakaoUser.getId();
//
//        UserVO existingUser = userMapper.selectByAccountId(accountId);
//        if(existingUser != null){
//            return existingUser;
//        }
//
//        UserVO newUser = UserVO.builder()
//                .accountId(accountId)
//                .email(kakaoUser.getEmail())
//                .name(kakaoUser.getNickname())
//                .password("kakao")
//                .provider(Provider.KAKAO)
//                .build();
//
//        log.info("새 사용자 생성: {}", newUser);
//
//        userMapper.insertUser(newUser);
//        return newUser;
//    }

}

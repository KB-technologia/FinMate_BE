package org.finmate.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.attendance.domain.UserAttendanceVO;
import org.finmate.attendance.mapper.UserAttendanceMapper;
import org.finmate.member.domain.KakaoUser;
import org.finmate.member.domain.UserVO;
import org.finmate.member.domain.enums.Provider;
import org.finmate.member.dto.KakaoTokenResponse;
import org.finmate.member.dto.KakaoUserResponse;
import org.finmate.member.mapper.UserMapper;
import org.finmate.security.dto.AuthResultDTO;
import org.finmate.security.dto.UserLoginInfoDTO;
import org.finmate.security.util.JwtProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class KaKaoServiceImpl implements KakaoService{

    private final JwtProcessor jwtProcessor;

    @Value("${kakao.client-id}")
    private String CLIENT_ID;

    @Value("${kakao.redirect-uri}")
    private String REDIRECT_URI;

    private final UserMapper userMapper;

    private final UserAttendanceMapper userAttendanceMapper;

    // 1. 인가 코드로 access token 받기
    @Override
    @Transactional
    public String getAccessToken(final String code){
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
    @Override
    @Transactional
    public KakaoUser getUserInfo(final String accessToken){
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
            String gender = kakaoResponse.getKakao_account().getGender();
            String birthyear = kakaoResponse.getKakao_account().getBirthyear();
            String birthday = kakaoResponse.getKakao_account().getBirthday();

            String birth = null;
            if(birthyear != null && birthday != null){
                birth = birthyear +"-"+birthday.substring(0,2) + "-"+birthday.substring(2);
            }
            log.info("getUserInfo - id: {}, nickname: {}, email: {}, gender: {}, birth: {}",
                    id, nickname, email, gender, birth);
            return new KakaoUser(id,
                    nickname != null ? nickname : "카카오사용자",
                    email != null ? email : "no-email@kakao.com",
                    gender, birth);
        }
        catch (Exception e) {
            log.error("카카오 사용자 정보 파싱 실패: {}", e.getMessage());
            throw new RuntimeException("카카오 사용자 정보 파싱 실패", e);
        }
    }

    @Override
    @Transactional
    public AuthResultDTO getOrCreateUserAndBuildAuthDTO(final KakaoUser kakaoUser){
        String accountId = "kakao_" + kakaoUser.getId();
        UserVO user = userMapper.selectByAccountId(accountId);

        boolean isNewUser = false;
        boolean rewardClaimed = false;
        int consecutiveDays = 0;
        if(user == null){
            user = UserVO.builder()
                    .accountId(accountId)
                    .email(kakaoUser.getEmail())
                    .name(kakaoUser.getNickname())
                    .password("kakao")
                    .provider(Provider.KAKAO)
                    .build();
            isNewUser = true;
        }
        else {
            UserAttendanceVO attendanceVO = userAttendanceMapper.getAttendanceByUserId(user.getId());
            rewardClaimed = attendanceVO.getRewardClaimed();
            consecutiveDays = attendanceVO.getConsecutiveDays();
        }

        String token = jwtProcessor.generateToken(user.getAccountId());

        UserLoginInfoDTO userLoginInfoDTO = UserLoginInfoDTO.builder()
                .accountId(accountId)
                .email(kakaoUser.getEmail())
                .name(kakaoUser.getNickname())
                .birth(kakaoUser.getBirth())
                .gender(kakaoUser.getGender())
                .roles(List.of("ROLE_USER"))
                .build();

        return new AuthResultDTO(token, userLoginInfoDTO, isNewUser, rewardClaimed, consecutiveDays);
    }
}

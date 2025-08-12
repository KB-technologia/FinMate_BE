package org.finmate.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.KakaoUser;
import org.finmate.member.domain.UserVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginInfoDTO {
    private String accountId;
    private String email;
    private String name;
    private String birth;
    private String gender;
    private List<String> roles;

    public static UserLoginInfoDTO of(UserVO user){
        return UserLoginInfoDTO.builder()
                .accountId(user.getAccountId())
                .email(user.getEmail())
                .name(user.getName())
                .birth(null)
                .gender(null)
                .roles(List.of("ROLE_USER"))
                .build();
    }

    public static UserLoginInfoDTO of(KakaoUser kakaoUser){
        return UserLoginInfoDTO.builder()
                .accountId("kakao_" + kakaoUser.getId())
                .email(kakaoUser.getEmail())
                .name(kakaoUser.getNickname())
                .birth(kakaoUser.getBirth())
                .gender(kakaoUser.getGender())
                .roles(List.of("ROLE_USER"))
                .build();
    }
}

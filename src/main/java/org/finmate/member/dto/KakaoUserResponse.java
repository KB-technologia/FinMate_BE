package org.finmate.member.dto;

import lombok.Data;

@Data
public class KakaoUserResponse {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public static class Properties {
        private String nickname;
    }

    @Data
    public static class KakaoAccount {
        private String email;
    }
}

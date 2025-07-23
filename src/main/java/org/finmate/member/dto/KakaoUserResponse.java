package org.finmate.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class KakaoUserResponse {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Properties {
        private String nickname;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class KakaoAccount {
        private String email;
    }
}

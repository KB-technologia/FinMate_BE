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
        private String gender;
        private String birthyear;
        private String birthday;
        // has_xx : 해당 값이 존재하고 사용자 동의했는지 여부 알려줌 (필수 동의라서 필요없지만, 선택항목으로 바뀔수도 있으니까..)
        private Boolean has_email;
        private Boolean has_gender;
        private Boolean has_birthyear;
        private Boolean has_birthday;
    }
}

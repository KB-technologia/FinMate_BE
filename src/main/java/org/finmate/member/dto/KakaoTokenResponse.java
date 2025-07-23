package org.finmate.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class KakaoTokenResponse {
    private String access_token;
    private String refresh_token;
    private int expires_in;
}
package org.finmate.member.dto;

import lombok.Data;

@Data
public class KakaoTokenResponse {
    private String access_token;
    private String refresh_token;
    private int expires_in;
}
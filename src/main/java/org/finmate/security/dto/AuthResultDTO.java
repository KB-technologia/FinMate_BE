package org.finmate.security.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "AuthResultDTO", description = "로그인/인증 결과 응답 DTO")
public class AuthResultDTO {
    @ApiModelProperty(value = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    @ApiModelProperty(value = "로그인한 사용자 정보")
    private UserLoginInfoDTO user;
    @ApiModelProperty(value = "신규 회원 여부(카카오)", example = "false")
    private boolean isNewUser;
    @ApiModelProperty(value = "출석 보상 수령 여부", example = "false")
    private boolean rewardClaimed;
    @ApiModelProperty(value = "연속 출석 일수", example = "3")
    private int consecutiveDays;
}

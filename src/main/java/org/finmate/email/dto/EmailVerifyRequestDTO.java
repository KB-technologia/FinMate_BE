package org.finmate.email.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "이메일 인증코드 검증 DTO")
public class EmailVerifyRequestDTO {
    @ApiModelProperty(value = "인증 요청 UUID", example = "a213...-....")
    private String requestId;

    @ApiModelProperty(value = "인증 코드", example = "123456")
    private String authCode;
}

package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "사용자 인증 DTO")
public class VerifyUserRequestDTO {
    @ApiModelProperty(value = "사용자 UUID", example = "548c2ae1..")
    private String uuid;
    @ApiModelProperty(value = "사용자 아이디", example = "hong123")
    private String accountId;
}


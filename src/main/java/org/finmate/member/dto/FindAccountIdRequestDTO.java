package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "아이디 찾기 UUID 요청 DTO")
public class FindAccountIdRequestDTO {
    @ApiModelProperty(value = "이메일 인증된 사용자 uuid", example = "548c2ae1..")
    private String uuid;
}

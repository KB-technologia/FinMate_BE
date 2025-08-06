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
@ApiModel(description = "이메일 인증 요청 DTO")
public class EmailRequestDTO {

    @ApiModelProperty(value = "사용자 이메일 주소", example = "user@example.com")
    private String email;
}

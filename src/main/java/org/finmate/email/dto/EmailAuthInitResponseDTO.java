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
@ApiModel(description = "이메일 인증코드 검증 응답 DTO")
public class EmailAuthInitResponseDTO {
    @ApiModelProperty(value = "인증된 UUID", example = "a213...-....")
    String uuid;
}

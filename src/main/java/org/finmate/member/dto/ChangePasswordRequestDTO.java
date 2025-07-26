package org.finmate.member.dto;

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
@ApiModel(description = "비밀번호 변경 DTO")
public class ChangePasswordRequestDTO {
    @ApiModelProperty(value = "현재 사용자 ID", example = "hong12345")
    private String accountId;
    @ApiModelProperty(value = "현재 사용자 uuid", example = "548c2ae1..")
    private String uuid;
    @ApiModelProperty(value = "새 비밀번호", example = "abcd789")
    private String newPassword;
}

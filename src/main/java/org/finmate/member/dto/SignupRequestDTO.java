package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원가입 요청", description = "회원가입 시 필요한 사용자 정보 DTO")
public class SignupRequestDTO {

    @ApiModelProperty(value = "사용자 이름", example = "홍길동")
    private String name;

    @ApiModelProperty(value = "사용자 계정 ID", example = "hong123")
    private String accountId;

    @ApiModelProperty(value = "이메일 주소", example = "hong@example.com")
    private String email;

    @ApiModelProperty(value = "비밀번호", example = "1234abcd")
    private String password;

    @ApiModelProperty(value = "비밀번호 확인", example = "1234abcd")
    private String passwordConfirm;

    @ApiModelProperty(value = "생년월일", example = "2000-01-01")
    private String birth;
}

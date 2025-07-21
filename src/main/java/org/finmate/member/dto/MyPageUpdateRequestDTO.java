package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.finmate.member.domain.MyPageVO;

@Getter
@Setter
@ApiModel(description = "마이페이지 수정 요청 DTO")
public class MyPageUpdateRequestDTO {

    @ApiModelProperty(value = "새 비밀번호", example = "newPassword123")
    private String password;

    @ApiModelProperty(value = "수정할 이메일", example = "newuser@example.com")
    private String email;

    @ApiModelProperty(value = "수정할 생년월일", example = "1995-05-01")
    private String birth;

    public MyPageVO toDomainWithAccountId(String accountId) {
        return new MyPageVO(accountId, this.email, this.birth);
    }
}

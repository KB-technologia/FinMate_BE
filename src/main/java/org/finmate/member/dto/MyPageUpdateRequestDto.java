package org.finmate.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.finmate.member.domain.MyPage;

@Getter
@Setter
public class MyPageUpdateRequestDto {
    private String password;
    private String email;
    private String birth;

    public MyPage toDomainWithAccountId(String accountId) {
        return new MyPage(accountId, this.email, this.birth);
    }
}

package org.finmate.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class MyPageVO {

    private final String accountId;
    private final String email;
    private final String birth;

    public MyPageVO(String accountId, String email, String birth) {
        this.accountId = accountId;
        this.email = email;
        this.birth = birth;
    }

    public MyPageVO updateEmail(String newEmail) {
        return new MyPageVO(this.accountId, newEmail, this.birth);
    }

    public MyPageVO updateBirth(String newBirth) {
        return new MyPageVO(this.accountId, this.email, newBirth);
    }
}

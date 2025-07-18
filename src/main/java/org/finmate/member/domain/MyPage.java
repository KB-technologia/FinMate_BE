package org.finmate.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class MyPage {

    private final String accountId;
    private final String email;
    private final String birth;

    public MyPage(String accountId, String email, String birth) {
        this.accountId = accountId;
        this.email = email;
        this.birth = birth;
    }

    public MyPage updateEmail(String newEmail) {
        return new MyPage(this.accountId, newEmail, this.birth);
    }

    public MyPage updateBirth(String newBirth) {
        return new MyPage(this.accountId, this.email, newBirth);
    }
}

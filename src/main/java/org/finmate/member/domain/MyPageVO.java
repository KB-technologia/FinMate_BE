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
    private final String animalName;
    private final String animalImage;

    public MyPageVO(String accountId, String email, String birth, String animalName, String animalImage) {
        this.accountId = accountId;
        this.email = email;
        this.birth = birth;
        this.animalName = animalName;
        this.animalImage = animalImage;
    }
    public MyPageVO updateEmail(String newEmail) {
        return new MyPageVO(this.accountId, newEmail, this.birth, this.animalName, this.animalImage);
    }

    public MyPageVO updateBirth(String newBirth) {
        return new MyPageVO(this.accountId, this.email, newBirth, this.animalName, this.animalImage);
    }
}

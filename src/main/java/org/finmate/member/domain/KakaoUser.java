package org.finmate.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUser {
    private String id;
    private String nickname;
    private String email;
    private String gender;
    private String birth;
}

package org.finmate.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageUpdateRequestDto {
    private String password;
    private String email;
    private String birth;
}
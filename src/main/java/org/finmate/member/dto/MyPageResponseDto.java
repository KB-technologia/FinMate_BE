package org.finmate.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponseDto {
    private String accountId;
    private String email;
    private String birth;
}

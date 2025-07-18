package org.finmate.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.finmate.member.domain.MyPage;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponseDto {
    private String accountId;
    private String email;
    private String birth;

    public static MyPageResponseDto from(MyPage myPage) {
        return MyPageResponseDto.builder()
                .accountId(myPage.getAccountId())
                .email(myPage.getEmail())
                .birth(myPage.getBirth())
                .build();
    }
}

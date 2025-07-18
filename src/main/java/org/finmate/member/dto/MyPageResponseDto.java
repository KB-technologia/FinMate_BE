package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.finmate.member.domain.MyPage;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "마이페이지 조회 응답 DTO")
public class MyPageResponseDto {

    @ApiModelProperty(value = "계정 ID", example = "user123")
    private String accountId;

    @ApiModelProperty(value = "이메일", example = "user@example.com")
    private String email;

    @ApiModelProperty(value = "생년월일", example = "1990-01-01")
    private String birth;

    public static MyPageResponseDto from(MyPage myPage) {
        return MyPageResponseDto.builder()
                .accountId(myPage.getAccountId())
                .email(myPage.getEmail())
                .birth(myPage.getBirth())
                .build();
    }
}

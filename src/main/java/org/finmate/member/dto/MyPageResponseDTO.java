package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.finmate.member.domain.MyPageVO;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "마이페이지 조회 응답 DTO")
public class MyPageResponseDTO {

    @ApiModelProperty(value = "계정 ID", example = "user123")
    private String accountId;

    @ApiModelProperty(value = "이메일", example = "user@example.com")
    private String email;

    @ApiModelProperty(value = "생년월일", example = "1990-01-01")
    private String birth;

    public static MyPageResponseDTO from(MyPageVO myPageVO) {
        return MyPageResponseDTO.builder()
                .accountId(myPageVO.getAccountId())
                .email(myPageVO.getEmail())
                .birth(myPageVO.getBirth())
                .build();
    }
}

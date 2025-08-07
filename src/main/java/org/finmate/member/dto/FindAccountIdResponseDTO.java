package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.finmate.member.domain.UserVO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "아이디 찾기 응답DTO")
public class FindAccountIdResponseDTO {
    @ApiModelProperty(value = "사용자 이름", example = "홍길동")
    private String name;
    @ApiModelProperty(value = "사용자 아이디", example = "hong123")
    private String accountId;

    public static FindAccountIdResponseDTO from(UserVO user) {
        return FindAccountIdResponseDTO.builder()
                .name(user.getName())
                .accountId(user.getAccountId())
                .build();
    }
}

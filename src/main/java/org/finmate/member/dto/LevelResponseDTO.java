package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "레벨 응답 DTO")
public class LevelResponseDTO {

    @ApiModelProperty(value = "현재 레벨", example = "2")
    private int currentLevel;

    @ApiModelProperty(value = "전체 경험치", example = "4500")
    private int totalExp;

    @ApiModelProperty(value = "캐릭터 변경권 획득 유무", example = "false")
    private boolean characterTicketAdded;

    @ApiModelProperty(value = "캐릭터 변경권 횟수", example = "0")
    private int characterTicket;
}

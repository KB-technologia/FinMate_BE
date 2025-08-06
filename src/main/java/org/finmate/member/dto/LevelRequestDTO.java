package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "레벨 요청 DTO")
public class LevelRequestDTO {

    @ApiModelProperty(value = "획득 경험치", example = "500")
    private int exp;
}

package org.finmate.assessment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssessmentDetailsDTO {

    @ApiModelProperty(value = "선택지 ID", example = "3")
    private Long id;

    @ApiModelProperty(value = "선택지 내용")
    private String content;

    @ApiModelProperty(value = "점수", example = "2")
    private Integer score;

    @ApiModelProperty(value = "태그", example = "균형형")
    private String tag;
}

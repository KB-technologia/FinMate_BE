package org.finmate.assessment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


// 사용자로부터 테스트 선택지를 받기 위한 DTO
@Data
@ApiModel(description = "사용자로부터 테스트 선택지를 받기 위한 DTO")
public class AssessmentRequestDTO {

    @ApiModelProperty(value = "사용자가 선택한 선택지에 따른 점수 리스트")
    private List<Integer> choices; // 사용자가 선택한 선택지들
}

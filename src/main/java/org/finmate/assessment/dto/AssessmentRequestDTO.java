package org.finmate.assessment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


// 사용자로부터 테스트 선택지를 받기 위한 DTO
@Data
@ApiModel(description = "사용자로부터 테스트 선택지를 받기 위한 DTO")
public class AssessmentRequestDTO {

    /**
     * userId는 추후 토큰 형식으로 리팩토링 예정
    **/

    @ApiModelProperty(value = "사용자 ID", example = "1")
    private Long userId; // 사용자 ID

    @ApiModelProperty(value = "사용자가 선택한 선택지에 따른 점수 리스트")
    private List<Integer> choices; // 사용자가 선택한 선택지들
}

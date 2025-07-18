package org.finmate.assessment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.assessment.domain.AssessmentDetailsVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
@ApiModel(description = "투자 성향 진단 테스트 선택지 DTO")
public class AssessmentDetailsDTO {

    @ApiModelProperty(value = "선택지 ID", example = "3")
    private Long id;

    @ApiModelProperty(value = "선택지 내용")
    private String content;

    @ApiModelProperty(value = "점수", example = "2")
    private Integer score;

    @ApiModelProperty(value = "태그", example = "균형형")
    private String tag;


    public static AssessmentDetailsDTO toDTO(AssessmentDetailsVO vo){

        return AssessmentDetailsDTO.builder()
                .id(vo.getId())
                .content(vo.getContent())
                .tag(vo.getTag())
                .score(vo.getScore())
                .build();
    }

}

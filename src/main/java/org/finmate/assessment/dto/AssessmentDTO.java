package org.finmate.assessment.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.assessment.domain.AssessmentVO;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "투자 성향 진단 테스트 질문지 DTO")
public class AssessmentDTO {

    @ApiModelProperty(value = "문항 ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "질문 텍스트")
    private String question;

    @ApiModelProperty(value = "선택지 목록")
    private List<AssessmentDetailsDTO> choices;

    /**
     * VO → DTO 변환
     */
    public static AssessmentDTO from(AssessmentVO vo) {
        return AssessmentDTO.builder()
                .id(vo.getId())
                .question(vo.getQuestion())
                .choices(vo.getChoices().stream()
                        .map(AssessmentDetailsDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }

}

package org.finmate.assessment.dto;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Data
public class AssessmentDTO {

    @ApiModelProperty(value = "문항 ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "질문 텍스트")
    private String question;

    @ApiModelProperty(value = "선택지 목록")
    private List<AssessmentDetailsDTO> choices;
}

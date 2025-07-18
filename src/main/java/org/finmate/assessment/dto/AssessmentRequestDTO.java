package org.finmate.assessment.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssessmentRequestDTO {

    /**
     * userId는 추후 토큰 형식으로 리팩토링 필요
    **/

    private Long userId;
    private List<Long> answers;
}

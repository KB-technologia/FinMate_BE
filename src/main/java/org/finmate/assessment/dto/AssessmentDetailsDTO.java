package org.finmate.assessment.dto;

import lombok.Data;

@Data
public class AssessmentDetailsDTO {

    private Long id;
    private String content;
    private int score;
    private String tag;
}

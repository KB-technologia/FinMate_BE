package org.finmate.assessment.dto;


import lombok.Data;

import java.util.List;

@Data
public class AssessmentDTO {

    private Long id;
    private String question;
    private List<AssessmentDetailsDTO> choices;
}

package org.finmate.assessment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentVO {

    private Long id;

    private List<AssessmentDetailsVO> choices;

    private String question;
}

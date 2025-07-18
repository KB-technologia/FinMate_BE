package org.finmate.assessment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDetailsVO {

    private Long id;

    private String content;

    private Integer score;

    private String tag;

}

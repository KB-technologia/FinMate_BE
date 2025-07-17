package org.finmate.assessment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AssessmentDetailsVO {

    private final Long id;

    private final String content;

    private final int score;

    private final String tag;

}

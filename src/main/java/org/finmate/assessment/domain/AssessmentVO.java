package org.finmate.assessment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AssessmentVO {

    private final Long id;

    private final List<AssessmentDetailsVO> choices;

    private final String question;
}

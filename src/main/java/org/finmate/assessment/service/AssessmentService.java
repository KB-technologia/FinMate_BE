package org.finmate.assessment.service;


import org.finmate.member.dto.UserInfoDTO;

import java.util.List;
import java.util.Optional;

public interface AssessmentService {
    UserInfoDTO resultAssessment(final Long userId, final List<Integer> choice);
}

package org.finmate.assessment.service;


import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.member.dto.UserInfoDTO;

import java.util.List;
import java.util.Optional;

public interface AssessmentService {

    // GET
    List<AssessmentDTO> loadAssessment();

    // POST
    Optional<UserInfoDTO> resultAssessment(Long userId, List<Integer> choice);
}

package org.finmate.assessment.service;


import org.finmate.assessment.dto.AssessmentDTO;

import java.util.List;

public interface AssessmentService {

    // GET
    List<AssessmentDTO> loadAssessment();

    // POST
    void resultAssessment(Long userId, List<Integer> choice);
}

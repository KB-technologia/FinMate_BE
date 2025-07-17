package org.finmate.assessment.service;

import lombok.RequiredArgsConstructor;
import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.assessment.mapper.AssessmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService{

    private final AssessmentMapper assessmentMapper;

    @Override
    public List<AssessmentDTO> loadAssessment() {
        return assessmentMapper.getList();
    }

    @Override
    public void resultAssessment(Long userId, List<Integer> choice) {

        // 유저 저장
    }
}

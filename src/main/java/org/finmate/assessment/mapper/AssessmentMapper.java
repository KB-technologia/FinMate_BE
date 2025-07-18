package org.finmate.assessment.mapper;


import org.finmate.assessment.domain.AssessmentDetailsVO;
import org.finmate.assessment.domain.AssessmentVO;
import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.assessment.dto.AssessmentDetailsDTO;

import java.util.List;

public interface AssessmentMapper {

    public List<AssessmentVO> getList();

    //public AssessmentDetailsVO getDetailsById(Long id);

    //int insertUserInfo(UserInfo userInfo);
}

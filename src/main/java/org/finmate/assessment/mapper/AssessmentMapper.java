package org.finmate.assessment.mapper;


import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.assessment.dto.AssessmentDetailsDTO;

import java.util.List;

public interface AssessmentMapper {

    public List<AssessmentDTO> getList();

    public AssessmentDetailsDTO getDetailsById(Long id);

    //int insertUserInfo(UserInfo userInfo);
}

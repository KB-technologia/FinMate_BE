package org.finmate.assessment.mapper;


import org.finmate.assessment.domain.AssessmentDetailsVO;
import org.finmate.assessment.domain.AssessmentVO;
import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.assessment.dto.AssessmentDetailsDTO;
import org.finmate.member.domain.UserInfoVO;

import java.util.List;

public interface AssessmentMapper {

    // 진단 테스트 질문지 반환
    public List<AssessmentVO> getList();

    // 각 선택지에 따른 점수 추출
    public AssessmentDetailsVO getDetailsById(Long id);

    // user_info 테이블에 저장
    int insertUserInfo(UserInfoVO userInfo);
}

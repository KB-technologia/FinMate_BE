package org.finmate.attendance.mapper;

import org.finmate.attendance.domain.UserAttendanceVO;

public interface QuizSolvedMapper {
    UserAttendanceVO getQuizSolved(Long userId);
    int updateQuizSolved(Long userId);
}

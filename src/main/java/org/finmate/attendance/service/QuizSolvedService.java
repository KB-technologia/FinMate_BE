package org.finmate.attendance.service;

import org.finmate.attendance.domain.UserAttendanceVO;
import org.finmate.attendance.dto.QuizSolvedDTO;

public interface QuizSolvedService {
    QuizSolvedDTO todaySolvedQuiz(final Long userId);
    void changeSolvedQuiz(final Long userId);
}

package org.finmate.attendance.mapper;

import org.finmate.attendance.domain.UserAttendanceVO;

public interface UserAttendanceMapper {
    UserAttendanceVO getAttendanceByUserId(Long userId);
    int checkInAttendance(UserAttendanceVO userAttendance);
    int resetAllRewards();
    Boolean getQuizSolved(Long userId);
    int updateQuizSolved(Long userId);
}

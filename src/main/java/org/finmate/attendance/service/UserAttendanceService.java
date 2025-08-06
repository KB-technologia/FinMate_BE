package org.finmate.attendance.service;

import org.finmate.attendance.dto.UserAttendanceDTO;

public interface UserAttendanceService {
    void resetDailyAttendanceRewards();
    UserAttendanceDTO getUserAttendance(Long userId);
    void checkInAttendance(Long userId);
}

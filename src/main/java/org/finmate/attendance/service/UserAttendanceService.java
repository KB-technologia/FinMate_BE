package org.finmate.attendance.service;

import org.finmate.attendance.dto.UserAttendanceDTO;

public interface UserAttendanceService {
    void resetDailyAttendanceRewards();
    UserAttendanceDTO getUserAttendance(final Long userId);
    void checkInAttendance(final Long userId);
}

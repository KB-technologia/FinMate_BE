package org.finmate.attendance.service;

import org.finmate.attendance.dto.UserAttendanceDTO;

public interface UserAttendanceService {
    UserAttendanceDTO getUserAttendance(final Long userId);
    void checkInAttendance(final Long userId);
}

package org.finmate.attendance.service;

import org.finmate.attendance.dto.UserAttendanceDTO;

public interface UserAttendanceService {
    UserAttendanceDTO getUserAttendance(Long userId);
    void checkInAttendance(Long userId);
}

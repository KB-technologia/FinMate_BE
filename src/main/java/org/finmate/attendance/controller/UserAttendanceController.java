package org.finmate.attendance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.attendance.domain.UserAttendanceVO;
import org.finmate.attendance.dto.UserAttendanceDTO;
import org.finmate.attendance.service.UserAttendanceService;
import org.finmate.member.domain.CustomUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/attendance")
public class UserAttendanceController {

    private final UserAttendanceService userAttendanceService;

    @GetMapping
    public ResponseEntity<UserAttendanceDTO> getUserAttendance(
            @ApiIgnore @AuthenticationPrincipal CustomUser customUser
            ) {
        Long userId = customUser.getUser().getId();
        return ResponseEntity.ok(userAttendanceService.getUserAttendance(userId));
    }

    @PostMapping
    public ResponseEntity<Void> checkInAttendance(
            @ApiIgnore @AuthenticationPrincipal CustomUser customUser
    ) {
        Long userId = customUser.getUser().getId();

        userAttendanceService.checkInAttendance(userId);
        return ResponseEntity.ok().build();
    }
}

package org.finmate.attendance.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.attendance.domain.UserAttendanceVO;
import org.finmate.attendance.dto.UserAttendanceDTO;
import org.finmate.attendance.service.UserAttendanceService;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.dto.UserInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/attendance")
@Api(tags = "사용자 출석 API")
public class UserAttendanceController {

    private final UserAttendanceService userAttendanceService;

    @GetMapping
    @ApiOperation(
            value = "사용자 출석 정보 확인",
            notes = "사용자의 첫 로그인, 연속 출석일 등 출석 정보 데이터를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 데이터가 반환되었습니다.",
                    response = UserAttendanceDTO.class)
    })
    public ResponseEntity<UserAttendanceDTO> getUserAttendance(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser
            ) {
        Long userId = customUser.getUser().getId();
        return ResponseEntity.ok(userAttendanceService.getUserAttendance(userId));
    }

    @PostMapping
    @ApiOperation(
            value = "사용자 출석 체크",
            notes = "사용자의 출석 체크를 진행합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 출석 체크가 진행되었습니다.")
    })
    public ResponseEntity<Void> checkInAttendance(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser
    ) {
        Long userId = customUser.getUser().getId();

        userAttendanceService.checkInAttendance(userId);
        return ResponseEntity.ok().build();
    }
}

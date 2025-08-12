package org.finmate.attendance.service;

import lombok.RequiredArgsConstructor;
import org.finmate.attendance.domain.UserAttendanceVO;
import org.finmate.attendance.dto.UserAttendanceDTO;
import org.finmate.attendance.mapper.UserAttendanceMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserAttendanceServiceImpl implements UserAttendanceService {
    private final UserAttendanceMapper userAttendanceMapper;

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시 0분 0초에 실행
    @Override
    public void resetDailyAttendanceRewards() {
        //TODO: 예외처리 필요
        userAttendanceMapper.resetAllRewards();
    }

    @Transactional
    @Override
    public UserAttendanceDTO getUserAttendance(final Long userId) {
        //TODO: 예외처리 필요
        return UserAttendanceDTO.from(
                userAttendanceMapper.getAttendanceByUserId(userId)
        );
    }

    @Transactional
    @Override
    public void checkInAttendance(final Long userId) {
        UserAttendanceVO userAttendanceVO = userAttendanceMapper.getAttendanceByUserId(userId);
        LocalDate lastAttendanceDate = LocalDate.now();
        if(userAttendanceVO != null) {
            lastAttendanceDate = userAttendanceVO.getUpdatedAt().toLocalDate();
        }

        int newConsecutiveDays = 0;
        if(lastAttendanceDate.plusDays(1).equals(LocalDate.now())) {
            newConsecutiveDays = userAttendanceVO.getConsecutiveDays() + 1;
        }
        UserAttendanceVO newUserAttendance = UserAttendanceVO.builder()
                .userId(userId)
                .rewardClaimed(true)
                .consecutiveDays(newConsecutiveDays)
                .build();

        //TODO: 예외처리 필요
        userAttendanceMapper.checkInAttendance(newUserAttendance);
    }
}

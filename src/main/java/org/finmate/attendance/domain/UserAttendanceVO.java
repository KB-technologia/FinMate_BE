package org.finmate.attendance.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserAttendanceVO {
    private final Long id;
    private final Long userId;
    private final Integer consecutiveDays;
    private final Boolean rewardClaimed;
    private final LocalDateTime updatedAt;
}

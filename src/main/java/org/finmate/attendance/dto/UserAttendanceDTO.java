package org.finmate.attendance.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.attendance.domain.UserAttendanceVO;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "출석체크 DTO", description = "현재 유저의 출석 체크 정보입니다.")
public class UserAttendanceDTO {
    @ApiModelProperty(value = "")
    private Boolean rewardClaimed;
    @ApiModelProperty(value = "")
    private Integer consecutiveDays;

    public static UserAttendanceDTO from(UserAttendanceVO userAttendanceVO) {
        return UserAttendanceDTO.builder()
                .consecutiveDays(userAttendanceVO.getConsecutiveDays())
                .rewardClaimed(userAttendanceVO.getRewardClaimed())
                .build();
    }
}

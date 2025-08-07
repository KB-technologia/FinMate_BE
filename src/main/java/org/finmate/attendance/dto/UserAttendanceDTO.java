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
@ApiModel(value = "사용자 출석 정보 DTO", description = "현재 유저의 출석 체크 정보입니다.")
public class UserAttendanceDTO {
    @ApiModelProperty(value = "첫 로그인인지 확인(출석을 했는지 안했는지 확인)")
    private Boolean rewardClaimed;
    @ApiModelProperty(value = "최대연속 출석일")
    private Integer consecutiveDays;

    public static UserAttendanceDTO from(final UserAttendanceVO userAttendanceVO) {
        return UserAttendanceDTO.builder()
                .consecutiveDays(userAttendanceVO.getConsecutiveDays())
                .rewardClaimed(userAttendanceVO.getRewardClaimed())
                .build();
    }
}

package org.finmate.quiz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.finmate.attendance.domain.UserAttendanceVO;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ApiModel(value ="퀴즈 풀이 체크 DTO", description = "유저의 퀴즈 풀이 여부 DTO")
public class QuizSolvedDTO {
    @ApiModelProperty(value = "유저가 오늘 퀴즈를 풀었는지 확인")
    private Boolean quizSolved;

    public static QuizSolvedDTO from(UserAttendanceVO userAttendanceVO) {
        return QuizSolvedDTO.builder()
                .quizSolved(userAttendanceVO.getQuizSolved())
                .build();
    }
}

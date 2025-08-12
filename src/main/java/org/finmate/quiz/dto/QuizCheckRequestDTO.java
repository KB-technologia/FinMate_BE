package org.finmate.quiz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "사용자의 정답 비교 DTO")
public class QuizCheckRequestDTO {

    @ApiModelProperty(value = "퀴즈 아이디")
    private Long quizId;

    @ApiModelProperty(value = "사용자가 선택한 정답", example ="true:O, false: X")
    private boolean answer;
}
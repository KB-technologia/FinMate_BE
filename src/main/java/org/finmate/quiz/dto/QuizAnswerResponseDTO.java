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
@ApiModel(description = "정답 결과 응답 DTO")
public class QuizAnswerResponseDTO {

    @ApiModelProperty(value = "정답 여부 (true: 정답, false: 오답)")
    private boolean correct;

    @ApiModelProperty(value = "해설 메시지")
    private String message;
}
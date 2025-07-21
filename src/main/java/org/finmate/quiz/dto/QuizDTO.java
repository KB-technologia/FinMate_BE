package org.finmate.quiz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.quiz.domain.QuizVO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "퀴즈 출제용 DTO")
public class QuizDTO {
    @ApiModelProperty(value = "퀴즈 아이디")
    private Long id;

    @ApiModelProperty(value = "퀴즈 문제")
    private String quiz;

    public static QuizDTO of (QuizVO vo){
        return vo == null? null : QuizDTO.builder()
                .id(vo.getId())
                .quiz(vo.getQuiz())
                .build();
    }
}
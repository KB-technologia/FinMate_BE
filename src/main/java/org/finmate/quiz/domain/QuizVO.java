package org.finmate.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizVO {
    private Long id;
    private String quiz;
    private boolean quizAnswer;
    private String correctAnswer;
    private String wrongAnswer;
}
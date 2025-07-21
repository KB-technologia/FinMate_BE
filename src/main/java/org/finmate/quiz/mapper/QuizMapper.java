package org.finmate.quiz.mapper;

import org.finmate.quiz.domain.QuizVO;

public interface QuizMapper {
    public QuizVO selectQuiz(Long quizId);
    public QuizVO selectRandomQuiz();
}


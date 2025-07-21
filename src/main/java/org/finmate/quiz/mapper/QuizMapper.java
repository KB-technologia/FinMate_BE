package org.finmate.quiz.mapper;

import org.finmate.quiz.domain.QuizVO;

import java.util.Optional;

public interface QuizMapper {
    public QuizVO selectRandomQuiz();
    public Optional<QuizVO> selectQuiz(Long quizId);
}


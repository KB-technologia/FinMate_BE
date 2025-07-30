package org.finmate.quiz.service;

import org.finmate.quiz.dto.QuizAnswerResponseDTO;
import org.finmate.quiz.dto.QuizCheckRequestDTO;
import org.finmate.quiz.dto.QuizDTO;


public interface QuizService {
    public QuizDTO getRandomQuiz();

    public QuizAnswerResponseDTO checkAnswer (QuizCheckRequestDTO dto);

}
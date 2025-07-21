package org.finmate.quiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.quiz.domain.QuizVO;
import org.finmate.quiz.dto.QuizCheckRequestDTO;
import org.finmate.quiz.dto.QuizDTO;
import org.finmate.quiz.mapper.QuizMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuizServiceImpl implements QuizService {
    private final QuizMapper quizMapper;

    public QuizDTO getRandomQuiz(){
        QuizVO randomQuiz = quizMapper.selectRandomQuiz();
        return QuizDTO.of(randomQuiz);
    }

    public String checkAnswer(QuizCheckRequestDTO dto) {
        QuizVO quiz = quizMapper.selectQuiz(dto.getQuizId());
        log.info(quiz.isQuizAnswer());
        log.info(dto.isAnswer());
        if(quiz.isQuizAnswer() == dto.isAnswer()){
            return quiz.getCorrectAnswer();
        } else {
            return quiz.getWrongAnswer();
        }
    }

    //TODO : 사용자가 정답을 맞췄을 시 경험치 지급 로직 추가
}
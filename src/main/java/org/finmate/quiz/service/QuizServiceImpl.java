package org.finmate.quiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.quiz.dto.QuizSolvedDTO;
import org.finmate.attendance.mapper.UserAttendanceMapper;
import org.finmate.quiz.domain.QuizVO;
import org.finmate.quiz.dto.QuizAnswerResponseDTO;
import org.finmate.quiz.dto.QuizCheckRequestDTO;
import org.finmate.quiz.dto.QuizDTO;
import org.finmate.quiz.mapper.QuizMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class QuizServiceImpl implements QuizService {
    private final QuizMapper quizMapper;
    private final UserAttendanceMapper userAttendanceMapper;

    @Override
    public QuizDTO getRandomQuiz(){
        QuizVO randomQuiz = quizMapper.selectRandomQuiz();
        return QuizDTO.from(randomQuiz);
    }

    @Override
    public QuizAnswerResponseDTO checkAnswer(QuizCheckRequestDTO dto) {
        QuizVO quiz = Optional.ofNullable(quizMapper.selectQuiz(dto.getQuizId()))
                .orElseThrow(() -> new RuntimeException("해당 퀴즈가 존재하지 않습니다."));

        boolean isCorrect = quiz.isQuizAnswer() == dto.isAnswer();
        return QuizAnswerResponseDTO.builder()
                .correct(isCorrect)
                .message(isCorrect ? quiz.getCorrectAnswer() : quiz.getWrongAnswer())
                .build();
    }
    //TODO : 사용자가 정답을 맞췄을 시 경험치 지급 로직 추가

    @Override
    @Transactional
    public QuizSolvedDTO todaySolvedQuiz(Long userId){
        //Todo : 예외처리
        Boolean solved = userAttendanceMapper.getQuizSolved(userId);
        return QuizSolvedDTO.builder().quizSolved(solved).build();
    }

    @Override
    @Transactional
    public void changeSolvedQuiz(Long userId){
        //Todo : 예외처리
        userAttendanceMapper.updateQuizSolved(userId);
    }
}
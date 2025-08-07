package org.finmate.attendance.service;

import lombok.RequiredArgsConstructor;
import org.finmate.attendance.dto.QuizSolvedDTO;
import org.finmate.attendance.dto.UserAttendanceDTO;
import org.finmate.attendance.mapper.QuizSolvedMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class QuizSolvedServiceImpl implements QuizSolvedService {
    private final QuizSolvedMapper quizSolvedMapper;

    @Override
    @Transactional
    public QuizSolvedDTO todaySolvedQuiz(Long userId){
        //Todo : 예외처리
        return QuizSolvedDTO.from(
                quizSolvedMapper.getQuizSolved(userId));
    }

    @Override
    @Transactional
    public void changeSolvedQuiz(Long userId){
        //Todo : 예외처리
        quizSolvedMapper.updateQuizSolved(userId);
    }
}

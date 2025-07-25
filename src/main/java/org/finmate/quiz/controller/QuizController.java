package org.finmate.quiz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.finmate.quiz.dto.QuizAnswerResponseDTO;
import org.finmate.quiz.dto.QuizCheckRequestDTO;
import org.finmate.quiz.dto.QuizDTO;
import org.finmate.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@Slf4j
@RequestMapping("/api/quiz")
@Api(tags = "Quiz API 컨트롤러")
public class QuizController {

    private final QuizService quizService;

    @ApiModelProperty(value = "랜덤 퀴즈 목록 생성", notes = "RAND()함수를 통해 랜덤 퀴즈 생성")
    @GetMapping
    public QuizDTO getQuiz(){
        return quizService.getRandomQuiz();
    }

    @ApiModelProperty(value = "퀴즈 정답 체크", notes = "사용자가 입력한 퀴즈의 정답과 DB에 저장된 퀴즈의 정답과 비교하여 해설 출력")
    @PostMapping("/check")
    public QuizAnswerResponseDTO checkQuiz(@RequestBody QuizCheckRequestDTO dto) {
        return quizService.checkAnswer(dto);
    }
}

package org.finmate.quiz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.finmate.quiz.dto.QuizAnswerResponseDTO;
import org.finmate.quiz.dto.QuizCheckRequestDTO;
import org.finmate.quiz.dto.QuizDTO;
import org.finmate.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@Slf4j
@RequestMapping("/api/quiz")
@Api(tags = "Quiz API 컨트롤러")
public class QuizController {

    private final QuizService quizService;

    @ApiOperation(value = "랜덤 퀴즈 목록 생성", notes = "RAND()함수를 통해 랜덤 퀴즈 생성")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = QuizDTO.class)
    @GetMapping
    public ResponseEntity<QuizDTO> getQuiz(){
        return ResponseEntity.ok(quizService.getRandomQuiz());
    }

    @ApiOperation(value = "퀴즈 정답 체크", notes = "사용자가 입력한 퀴즈의 정답과 DB에 저장된 퀴즈의 정답과 비교하여 해설 출력")
    @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = QuizAnswerResponseDTO.class)
    @PostMapping("/check")
    public ResponseEntity<QuizAnswerResponseDTO> checkQuiz(@RequestBody final QuizCheckRequestDTO dto) {
        return ResponseEntity.ok(quizService.checkAnswer(dto));
    }
}
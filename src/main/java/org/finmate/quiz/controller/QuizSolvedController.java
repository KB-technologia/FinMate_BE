package org.finmate.quiz.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.quiz.dto.QuizSolvedDTO;
import org.finmate.member.domain.CustomUser;
import org.finmate.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/quizsolved")
public class QuizSolvedController {

    private final QuizService quizService;

    @GetMapping
    @ApiOperation(
            value = "사용자 퀴즈 풀이 여부 확인",
            notes = "사용자의 퀴즈 풀이 여부를 반환"
    )
    public ResponseEntity<QuizSolvedDTO> getQuizSolved(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser) {
        Long userId = customUser.getUser().getId();
        QuizSolvedDTO solved = quizService.todaySolvedQuiz(userId);
        return ResponseEntity.ok(solved);
    }

    @PatchMapping
    @ApiOperation(
            value = "사용자 퀴즈 풀이 여부 변경",
            notes = "사용자의 퀴즈 풀이 여부 컬럼 값을 변경"
    )
    public ResponseEntity<Void> updateQuizSolved(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser){
        Long userId = customUser.getUser().getId();
        quizService.changeSolvedQuiz(userId);
        return ResponseEntity.ok().build();
    }
}
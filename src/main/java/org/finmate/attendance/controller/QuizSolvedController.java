package org.finmate.attendance.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.attendance.dto.QuizSolvedDTO;
import org.finmate.attendance.service.QuizSolvedService;
import org.finmate.member.domain.CustomUser;
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

    private final QuizSolvedService quizSolvedService;

    @GetMapping
    @ApiOperation(
            value = "사용자 퀴즈 풀이 여부 확인",
            notes = "사용자의 퀴즈 풀이 여부를 반환"
    )
    public ResponseEntity<QuizSolvedDTO> getQuizSolved(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser) {
        Long userId = customUser.getUser().getId();
        QuizSolvedDTO solved = quizSolvedService.todaySolvedQuiz(userId);
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
        quizSolvedService.changeSolvedQuiz(userId);
        return ResponseEntity.ok().build();
    }
}

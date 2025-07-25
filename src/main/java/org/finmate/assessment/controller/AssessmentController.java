package org.finmate.assessment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.assessment.dto.AssessmentRequestDTO;
import org.finmate.assessment.service.AssessmentService;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.dto.UserInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessment")
@Api(tags = "투자 성향 진단 테스트 API")
public class AssessmentController {


    private final AssessmentService assessmentService;

    /**
     * 질문 리스트 반환
     */
    @GetMapping("")
    @ApiOperation(
            value = "테스트 문항 조회",
            notes = "스토리텔링 기반 진단 테스트 문항들을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 테스트 문항을 조회했습니다.",
                    response = AssessmentDTO.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<AssessmentDTO>> getList(){

        return ResponseEntity.ok(assessmentService.loadAssessment());
    }

    /**
     * 투자 성향 테스트 결과 user_info 반환 및 저장
     */
    @PostMapping("")
    public ResponseEntity<UserInfoDTO> resultAssessment(
            @AuthenticationPrincipal CustomUser customUser,
            @RequestBody AssessmentRequestDTO requestDTO
    ){
        Long userId = customUser.getUser().getId();
        List<Integer> choices = requestDTO.getChoices();

        return ResponseEntity.of(assessmentService.resultAssessment(userId, choices));
    }


}

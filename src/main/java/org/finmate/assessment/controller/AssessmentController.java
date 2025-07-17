package org.finmate.assessment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.assessment.service.AssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/assessment")
@Api(tags = "투자 성향 테스트 API")
public class AssessmentController {


    private final AssessmentService assessmentService;

    @ApiOperation(
            value = "진단 문항 조회",
            notes = "스토리텔링 기반 설문 문항 리스트를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 진단 문항을 조회했습니다.",
                    response = AssessmentDTO.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("")
    public ResponseEntity<List<AssessmentDTO>> getList(){

        return ResponseEntity.ok(assessmentService.loadAssessment());
    }
}

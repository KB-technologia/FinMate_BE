package org.finmate.assessment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.assessment.dto.AssessmentRequestDTO;
import org.finmate.assessment.service.AssessmentService;
import org.finmate.member.domain.CustomUser;

import org.finmate.member.dto.UserInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessment")
@Api(tags = "투자 성향 진단 테스트 API")
public class AssessmentController {

    private final AssessmentService assessmentService;

    /**
     * 투자 성향 테스트 결과 user_info 반환 및 저장
     */
    @PostMapping
    @ApiOperation(
            value = "테스트 결과 저장",
            notes = "사용자의 테스트 결과를 저장하고, 해당 결과를 기반으로 한 유저 정보를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 테스트 결과가 저장되었습니다.",
                    response = UserInfoDTO.class)
    })
    public ResponseEntity<UserInfoDTO> resultAssessment(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser,
            @RequestBody final AssessmentRequestDTO requestDTO
    ){
        Long userId = customUser.getUser().getId();
        List<Integer> choices = requestDTO.getChoices();

        return ResponseEntity.ok(assessmentService.resultAssessment(userId, choices));
    }
}

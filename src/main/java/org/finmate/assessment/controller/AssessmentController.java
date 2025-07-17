package org.finmate.assessment.controller;

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
public class AssessmentController {


    private final AssessmentService assessmentService;

    @GetMapping("")
    public ResponseEntity<List<AssessmentDTO>> getList(){

        return ResponseEntity.ok(assessmentService.loadAssessment());
    }
}

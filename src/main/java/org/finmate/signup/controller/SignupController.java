package org.finmate.signup.controller;

import lombok.RequiredArgsConstructor;
import org.finmate.signup.dto.SignupRequestDto;
import org.finmate.signup.service.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody SignupRequestDto dto) {
        signupService.signup(dto);
        return ResponseEntity.ok("회원가입 성공");
    }
}

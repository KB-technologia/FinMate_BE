package org.finmate.signup.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.finmate.signup.dto.SignupRequestDto;
import org.finmate.signup.service.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "회원가입 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @ApiOperation(value = "회원가입", notes = "회원 정보를 입력받아 회원가입을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회원가입 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody SignupRequestDto dto) {
        signupService.signup(dto);
        return ResponseEntity.ok("회원가입 성공");
    }
}

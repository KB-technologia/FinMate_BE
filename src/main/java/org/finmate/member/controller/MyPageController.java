package org.finmate.member.controller;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDto;
import org.finmate.member.dto.MyPageUpdateRequestDto;
import org.finmate.member.service.MyPageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/me")
    public MyPageResponseDto getMyPage() {
        Long userId = 1L; // TODO: 인증 처리 후 교체
        return myPageService.getMyPageInfo(userId);
    }

    @PatchMapping("/me")
    public void updateMyPage(@RequestBody MyPageUpdateRequestDto dto) {
        Long userId = 1L; // TODO: 인증 처리 후 교체
        myPageService.updateMyPageInfo(userId, dto);
    }
}

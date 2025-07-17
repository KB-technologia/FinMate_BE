package org.finmate.member.controller;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDto;
import org.finmate.member.service.MyPageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MyPageController {

    private final MyPageService myPageService;

    // TODO: 임시로 userId를 하드코딩 (나중엔 인증 정보에서 추출 예정)
    @GetMapping("/me")
    public MyPageResponseDto getMyPage() {
        Long userId = 1L; // TODO: 인증 처리 후 교체
        return myPageService.getMyPageInfo(userId);
    }
}

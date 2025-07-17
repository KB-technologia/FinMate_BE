package org.finmate.member.controller;

import lombok.RequiredArgsConstructor;
import org.finmate.member.service.MyPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageViewController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public String myPage(Model model) {
        Long userId = 1L;
        model.addAttribute("myInfo", myPageService.getMyPageInfo(userId));
        return "mypage";
    }
}

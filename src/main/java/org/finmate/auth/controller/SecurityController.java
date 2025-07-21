package org.finmate.auth.controller;

import lombok.extern.log4j.Log4j2;
import org.finmate.auth.domain.CustomUser;
import org.finmate.auth.domain.UserVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/security")
@Controller
@Log4j2
public class SecurityController {
    @GetMapping("/all")
    public void doAll(){
        log.info("모든 사용자 접근 가능");
    }

    @GetMapping("/member")
    public void doMember(@AuthenticationPrincipal CustomUser customUser){
        UserVO user = customUser.getUser();
        log.info("로그인한 사용자 : accountId = {}, email = {}", user.getAccountId(), user.getEmail());
    }

    @GetMapping("/admin")
    public void doAdmin(@AuthenticationPrincipal CustomUser customUser){
        UserVO user = customUser.getUser();
        log.info("관리자 접근: accountId = {}, email = {}", user.getAccountId(), user.getEmail());
    }
}

package org.finmate.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        org.springframework.security.core.AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String message = "로그인 실패: ";
        if (exception instanceof BadCredentialsException) {
            message += "아이디 또는 비밀번호가 틀렸습니다.";
        } else {
            message += exception.getMessage();
        }

        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}

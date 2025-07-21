package org.finmate.security.handler;

import lombok.extern.log4j.Log4j2;
import org.finmate.security.util.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException{
        log.warn("인증 실패 : {}", authException.getMessage());
        JsonResponse.sendError(response, HttpStatus.UNAUTHORIZED, "인증이 필요합니다.");
    }
}

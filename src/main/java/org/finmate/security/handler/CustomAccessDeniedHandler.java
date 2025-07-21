package org.finmate.security.handler;

import lombok.extern.log4j.Log4j2;
import org.finmate.security.util.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;


@Log4j2
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException{
        log.warn("권한 부족 : {}", accessDeniedException.getMessage());
        JsonResponse.sendError(response, HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }
}

package org.finmate.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.finmate.security.util.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.security.SignatureException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class AuthenticationErrorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        try{
            filterChain.doFilter(request, response);
        }
        catch(ExpiredJwtException e){
            log.warn("만료된 JWT 토큰 : {}", e.getMessage());
            JsonResponse.sendError(response, HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        }
        catch(UnsupportedJwtException | MalformedJwtException | SignatureException e){
            log.warn("잘못된 JWT 토큰 : {}", e.getMessage());
            JsonResponse.sendError(response, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }
        catch(ServletException | RuntimeException e){
            log.error("필터 처리 중 예외 발생 : {}", e.getMessage());
            JsonResponse.sendError(response, HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
        }
    }
}

package org.finmate.security.filter;

import lombok.extern.log4j.Log4j2;
import org.finmate.security.dto.LoginDTO;
import org.finmate.security.handler.LoginFailureHandler;
import org.finmate.security.handler.LoginSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public JwtUsernamePasswordAuthenticationFilter(
            AuthenticationManager authenticationManager,
            LoginSuccessHandler successHandler,
            LoginFailureHandler failureHandler
    ){
        super(authenticationManager);
        setFilterProcessesUrl("/auth/login"); //POST 로그인 요청 url
        setAuthenticationSuccessHandler(successHandler); // 로그인 성공 핸들러 등록
        setAuthenticationFailureHandler(failureHandler); // 로그인 실패 핸들러 등록
    }

    //로그인 요청 URL인 경우 로그인 작업 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException{
        LoginDTO loginDTO = LoginDTO.of(request);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());

        return getAuthenticationManager().authenticate(authToken);

    }

}

package org.finmate.security.config;

import org.finmate.auth.handler.LoginFailureHandler;
import org.finmate.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    // 문자셋 필터
    public CharacterEncodingFilter encodingFilter(){
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        //http.addFilterBefore(encodingFilter(), CsrfFilter.class);
        http.csrf().disable();

        // 경로별 접근 권한 설정
        http.authorizeRequests()
                .antMatchers("/security/all/**").permitAll()
                .antMatchers("/security/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/security/member/**").access("hasAnyRole('MEMBER', 'ADMIN')");

        // 임시 테스트용
        http.formLogin()
            .loginProcessingUrl("/security/login")
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler);

        http.logout()
                .logoutUrl("/security/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
                .deleteCookies("remember-me", "JSESSIONID");


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //테스트용 계정
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}1234") // 암호화 안 한 평문 비밀번호 "1234" (테스트용)
                .roles("ADMIN", "MEMBER");
        auth.inMemoryAuthentication()
                .withUser("member")
                .password("{noop}1234")
                .roles("MEMBER");

    }

}

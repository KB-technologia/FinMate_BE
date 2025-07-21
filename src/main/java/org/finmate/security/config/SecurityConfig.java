package org.finmate.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.security.filter.AuthenticationErrorFilter;
import org.finmate.security.filter.JwtAuthenticationFilter;
import org.finmate.security.filter.JwtUsernamePasswordAuthenticationFilter;
import org.finmate.security.handler.CustomAccessDeniedHandler;
import org.finmate.security.handler.CustomAuthenticationEntryPoint;
import org.finmate.security.handler.LoginFailureHandler;
import org.finmate.security.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;

@Log4j2
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"org.finmate.auth.service", "org.finmate.auth", "org.finmate.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationErrorFilter authenticationErrorFilter;
    private final PasswordEncoder passwordEncoder;


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    // 문자셋 필터 (한글 인코딩 필터)
    public CharacterEncodingFilter encodingFilter(){
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    // AuthenticationManager 빈 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // cross origin 접근 허용
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // 접근 제한 무시 경로 설정 - resource
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/assets/**", "/api/member/**");
    }

    // Http 보안 설정 (JWT 기반)
    @Override
    public void configure(HttpSecurity http) throws Exception{
        //jwt 인증 필터 등록 (기존 필터 앞)
        JwtUsernamePasswordAuthenticationFilter jwtFilter = new JwtUsernamePasswordAuthenticationFilter(authenticationManagerBean(), loginSuccessHandler, loginFailureHandler);

        http.addFilterBefore(encodingFilter(), CsrfFilter.class) // 한글 인코딩 필터 설정
                .addFilterBefore(authenticationErrorFilter, UsernamePasswordAuthenticationFilter.class) // 인증 에러 필터
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //jwt 인증 필터
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // 로그인 인증 필터
                .httpBasic().disable() // 기본 HTTP 인증비활성화
                .csrf().disable()  // CSRF 비활성화
                .formLogin().disable()  // formLogin 비활성화- 관련 필터 해제
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 모드 설정
                .and()
                .exceptionHandling() // 예외 처리 설정
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }
}

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
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

@Log4j2
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"org.finmate.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationErrorFilter authenticationErrorFilter;
    private final PasswordEncoder passwordEncoder;



    // 문자셋 필터 (한글 인코딩 필터)
    public CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    // AuthenticationManager 빈 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 접근 제한 무시 경로 설정 - resource
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/api/member/**");
    }

    // Http 보안 설정 (JWT 기반)
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //jwt 인증 필터 등록 (기존 필터 앞)
        JwtUsernamePasswordAuthenticationFilter jwtFilter = new JwtUsernamePasswordAuthenticationFilter(authenticationManagerBean(), loginSuccessHandler, loginFailureHandler);

        http.addFilterBefore(encodingFilter(), CsrfFilter.class) // 한글 인코딩 필터 설정
                .cors()
                .and()
                .authorizeRequests()//권한 설정
                .antMatchers("/api/assessment").authenticated() //AssessmentController 모든 요청은 로그인 필요(투자 성향 진단 테스트)
                .antMatchers("/api/member/character/**").authenticated() //캐릭터 조회, 캐릭터 변경
                .antMatchers("/api/member/emailauthentication/**").permitAll() //이메일 인증 (인증코드 이메일로 전송, 인증코드 검증)
                .antMatchers("/auth/kakao/callback").permitAll() // 카카오 로그인 (카카오 인가코드 -> JWT발급)
                .antMatchers(HttpMethod.GET, "/api/member/me").authenticated() // 마이페이지 조회
                .antMatchers(HttpMethod.PATCH, "/api/member/me").authenticated() // 마이페이지 수정
                .antMatchers(HttpMethod.DELETE, "/api/member/withdraw").authenticated() // 회원 탈퇴
                .antMatchers("/api/member/join").permitAll() // 회원가입
                .antMatchers("/api/member/portfolio").authenticated() // 포트폴리오 등록/조회/수정/삭제
                .antMatchers("/api/product/favorite/**").authenticated() // 즐겨찾기 조회/등록/삭제
                .antMatchers(HttpMethod.POST, "/api/product/*/review").authenticated() // 리뷰 등록
                .antMatchers(HttpMethod.DELETE, "/api/product/*/review").authenticated() //리뷰 삭제
                .antMatchers("/api/product/**").permitAll() // 전체 상품 조회/상품 비교/ 상품 상세 보기/ 리뷰 조회
                .antMatchers("/api/product/filter/**").permitAll() // 조건별 상품 조회
                .antMatchers("/api/quiz/**").permitAll() // 랜덤 퀴즈 조회/퀴즈 정답 제출 및 해설 확인
                .anyRequest().permitAll()
                .and()
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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }
}

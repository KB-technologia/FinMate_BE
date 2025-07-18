package org.finmate.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.auth.domain.CustomUser;
import org.finmate.auth.domain.LoginProvider;
import org.finmate.auth.domain.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 요청 아이디 : {}", username);

        //테스트용 사용자 데이터
        if(!username.equals("testuser")){
            throw new UsernameNotFoundException(username + "은 존재하지 않는 사용자입니다. ");
        }

        //나중에 mapper로 바꿔야 하는 부분..
        UserVO user = UserVO.builder()
                .id(1L)
                .accountId("testuser")
                .password(passwordEncoder.encode("1234"))
                .name("테스트 유저")
                .email("test@example.com")
                .provider(LoginProvider.LOCAL)
                .build();

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new CustomUser(user, authorities);

    }
}

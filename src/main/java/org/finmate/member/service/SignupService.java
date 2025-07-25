package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.mapper.UserMapper;
import org.finmate.member.dto.SignupRequestDTO;
import org.finmate.member.domain.UserVO;
import org.finmate.member.domain.Provider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if (userMapper.existsByAccountId(dto.getAccountId())) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

            UserVO user = UserVO.builder()
                .name(dto.getName())
                .accountId(dto.getAccountId())
                .email(dto.getEmail())
                .password(encodedPassword)
                .birth(LocalDate.parse(dto.getBirth()))
                .provider(Provider.LOCAL)
                .createdAt(LocalDateTime.now())
                .build();


        userMapper.insertUser(user);
    }

    public boolean isAccountIdDuplicate(String accountId) {
        return userMapper.existsByAccountId(accountId);
    }
}

package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.mapper.UserMapper;
import org.finmate.member.dto.SignupRequestDTO;
import org.finmate.member.domain.UserVO;
import org.finmate.member.domain.Provider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserMapper userMapper;

    public void signup(SignupRequestDTO dto) {
            UserVO user = UserVO.builder()
                .name(dto.getName())
                .accountId(dto.getAccountId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .birth(LocalDate.parse(dto.getBirth()))
                .provider(Provider.LOCAL)
                .createdAt(LocalDateTime.now())
                .build();


        userMapper.insertUser(user);
    }
}

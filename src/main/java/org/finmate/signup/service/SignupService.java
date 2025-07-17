package org.finmate.signup.service;

import lombok.RequiredArgsConstructor;
import org.finmate.signup.mapper.UserMapper;
import org.finmate.signup.dto.SignupRequestDto;
import org.finmate.signup.domain.UserVO;
import org.finmate.signup.domain.Provider;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserMapper userMapper;

    public void signup(SignupRequestDto dto) {
        UserVO user = new UserVO();
        user.setName(dto.getName());
        user.setAccountId(dto.getAccountId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBirth(dto.getBirth());
        user.setProvider(Provider.LOCAL); // 무조건 LOCAL
        user.setCreateAt(LocalDateTime.now());

        userMapper.insertUser(user);
    }
}

package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.domain.enums.*;
import org.finmate.member.mapper.UserInfoMapper;
import org.finmate.member.mapper.UserMapper;
import org.finmate.member.dto.SignupRequestDTO;
import org.finmate.member.domain.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.finmate.member.domain.enums.Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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
                .provider(Provider.LOCAL)
                .createdAt(LocalDateTime.now())
                .build();
        userMapper.insertUser(user);

        UserInfoVO userInfo = UserInfoVO.builder()
                .userId(user.getId())
                .birth(LocalDate.parse(dto.getBirth()))
                .gender(Gender.valueOf(dto.getGender()))
                .isMarried(dto.getIsMarried())
                .hasJob(dto.getHasJob())
                .usesPublicTransport(dto.getUsesPublicTransport())
                .doesExercise(dto.getDoesExercise())
                .travelsFrequently(dto.getTravelsFrequently())
                .hasChildren(dto.getHasChildren())
                .hasHouse(dto.getHasHouse())
                .employedAtSme(dto.getEmployedAtSme())
                .usesMicroloan(dto.getUsesMicroloan())
                .exp(0)
                .updatedAt(LocalDateTime.now())
                .build();
        userInfoMapper.insertUserInfo(userInfo);
    }

    public boolean isAccountIdDuplicate(String accountId) {
        return userMapper.existsByAccountId(accountId);
    }
}

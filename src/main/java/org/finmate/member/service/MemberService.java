package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.dto.UserStatResponseDTO;
import org.finmate.member.mapper.UserInfoMapper;
import org.finmate.member.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserMapper userMapper;

    private final UserInfoMapper userInfoMapper;

    @Transactional
    public void withdraw(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        userMapper.deleteUserInfoByUserId(userId);
        userMapper.deleteUserAttendanceByUserId(userId);
        userMapper.deleteUserById(userId);
    }

    @Transactional
    public UserStatResponseDTO getStatByUserId(Long userId) {
        UserInfoVO vo = userInfoMapper.getUserInfoById(userId);
        return UserStatResponseDTO.builder()
                .adventureScore(vo.getAdventureScore())
                .valueTag(vo.getValueTag())
                .speedTag(vo.getSpeedTag())
                .strategyTag(vo.getStrategyTag())
                .financeScore(vo.getFinanceScore())
                .build();
    }
}


package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserMapper userMapper;

    @Transactional
    public void withdraw(String accountId) {
        Long userId = userMapper.findUserIdByAccountId(accountId);

        if (userId == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        userMapper.deleteUserInfoByUserId(userId);
        userMapper.deleteUserAttendanceByUserId(userId);
        userMapper.deleteUserById(userId);
    }
}


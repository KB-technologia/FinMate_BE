package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserMapper userMapper;

    public void withdraw(String accountId) {
        userMapper.deleteByAccountId(accountId);
    }
}

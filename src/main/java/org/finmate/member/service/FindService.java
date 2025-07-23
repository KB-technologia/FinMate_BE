package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.mapper.EmailAuthMapper;
import org.finmate.member.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindService {

    private final EmailAuthMapper emailAuthMapper;
    private final UserMapper userMapper;

    public String findAccountIdByUuid(String uuid) {
        String email = emailAuthMapper.findEmailByVerifiedUuid(uuid);
        if (email == null) return null;
        return userMapper.findAccountIdByEmail(email);
    }

    public boolean verifyUser(String uuid, String accountId) {
        String email = emailAuthMapper.findEmailByVerifiedUuid(uuid);
        if (email == null) return false;
        return userMapper.existsByAccountIdAndEmail(accountId, email);
    }

    public boolean resetPassword(String accountId, String newPassword) {
        return userMapper.updatePassword(accountId, newPassword) > 0;
    }
}


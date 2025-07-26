package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.EmailAuthVO;
import org.finmate.member.dto.ChangePasswordRequestDTO;
import org.finmate.member.mapper.EmailAuthMapper;
import org.finmate.member.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRecoveryService {

    private final EmailAuthMapper emailAuthMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

    public void resetPassword(ChangePasswordRequestDTO dto) {
        String accountId = dto.getAccountId();
        String uuid = dto.getUuid();
        String newPassword = dto.getNewPassword();

        EmailAuthVO auth = emailAuthMapper.findByUuid(uuid);
        if (auth == null || !auth.getIsVerified()) {
            throw new IllegalArgumentException("유효하지 않은 인증 정보입니다.");
        }

        if (!userMapper.existsByAccountId(accountId)) {
            throw new IllegalArgumentException("존재하지 않는 계정입니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);

        userMapper.updatePassword(accountId, encodedPassword);
    }
    }


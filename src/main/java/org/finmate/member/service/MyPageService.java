package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDTO;
import org.finmate.member.dto.MyPageUpdateRequestDTO;
import org.finmate.member.domain.MyPageVO;
import org.finmate.member.domain.EmailAuthVO;
import org.finmate.member.mapper.MyPageMapper;
import org.finmate.member.mapper.EmailAuthMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;
    private final EmailAuthMapper emailAuthMapper;
    private final PasswordEncoder passwordEncoder;

    public MyPageResponseDTO getMyPageInfo(Long userId) {
        MyPageVO myPageVO = myPageMapper.findMyPageInfo(userId);
        return MyPageResponseDTO.from(myPageVO);
    }

    public void updateMyPageInfo(Long userId, MyPageUpdateRequestDTO dto) {
        if (dto.getEmail() != null && dto.getEmailVerificationUUID() != null) {
            EmailAuthVO auth = emailAuthMapper.findByUuid(dto.getEmailVerificationUUID());

            if (auth == null || !auth.getIsVerified()) {
                throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
            }

            if (!dto.getEmail().equals(auth.getEmail())) {
                throw new IllegalArgumentException("인증된 이메일과 수정 요청 이메일이 일치하지 않습니다.");
            }
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            myPageMapper.updateUserWithPassword(userId, encodedPassword, dto.getEmail());
        } else if (dto.getEmail() != null) {
            myPageMapper.updateUserWithoutPassword(userId, dto.getEmail());
        }

        if (dto.getBirth() != null) {
            myPageMapper.updateUserInfo(userId, dto.getBirth());
        }
    }
}
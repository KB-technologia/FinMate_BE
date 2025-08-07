package org.finmate.mypage.service;

import lombok.RequiredArgsConstructor;
import org.finmate.email.domain.EmailAuthVO;
import org.finmate.email.mapper.EmailAuthMapper;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.mapper.UserInfoMapper;
import org.finmate.mypage.domain.MyPageVO;
import org.finmate.mypage.dto.MyPageResponseDTO;
import org.finmate.mypage.dto.MyPageUpdateRequestDTO;
import org.finmate.mypage.dto.UserStatResponseDTO;
import org.finmate.mypage.mapper.MyPageMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MyPageMapper myPageMapper;
    private final EmailAuthMapper emailAuthMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoMapper userInfoMapper;

    @Override
    public MyPageResponseDTO getMyPageInfo(final Long userId) {
        MyPageVO myPageVO = myPageMapper.findMyPageInfo(userId);
        return MyPageResponseDTO.from(myPageVO);
    }

    @Override
    @Transactional
    public void updateMyPageInfo(final Long userId, final MyPageUpdateRequestDTO dto) {
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

    @Transactional
    @Override
    public UserStatResponseDTO getStatByUserId(final Long userId) {
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

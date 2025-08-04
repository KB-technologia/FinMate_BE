package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDTO dto) {

        try {
            log.info("[SignupService] 전달받은 provider: {}", dto.getProvider());

            Provider provider = dto.getProvider() != null ? Provider.valueOf(dto.getProvider().toUpperCase()) : Provider.LOCAL;

            if (userMapper.existsByAccountId(dto.getAccountId())) {
                throw new RuntimeException("이미 사용 중인 아이디입니다.");
            }

            String encodedPassword;
            switch(provider){
                case LOCAL:
                    if(!dto.getPassword().equals(dto.getPasswordConfirm())){
                        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                    }
                    encodedPassword = passwordEncoder.encode(dto.getPassword());
                    break;

                case KAKAO:
                    encodedPassword = "kakao"; //더미 비밀번호
                    break;

                default :
                    throw new IllegalArgumentException("지원하지 않는 provider입니다: " + provider);

            }

            UserVO user = UserVO.builder()
                    .name(dto.getName())
                    .accountId(dto.getAccountId())
                    .email(dto.getEmail())
                    .password(encodedPassword)
                    .provider(provider)
                    .createdAt(LocalDateTime.now())
                    .build();
            userMapper.insertUser(user);

            log.debug("User inserted: {}", user);

            UserInfoVO userInfo = UserInfoVO.builder()
                    .userId(user.getId())
                    .birth(LocalDate.parse(dto.getBirth()))
                    .gender(Gender.valueOf(dto.getGender().toUpperCase()))
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
            log.debug("UserInfo inserted: {}", userInfo);

        }
        catch (RuntimeException e) {
            log.error("회원가입 처리 중 예외 발생: {}", e.getMessage(), e);
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
        }


    }

    public boolean isAccountIdDuplicate(String accountId) {
        return userMapper.existsByAccountId(accountId);
    }
}

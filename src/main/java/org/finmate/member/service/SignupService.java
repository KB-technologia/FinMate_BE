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


            String provider = dto.getProvider() != null ? dto.getProvider().toUpperCase() : "LOCAL";

            // 비밀번호 체크( LOCAL일 때만)
            if ("LOCAL".equals(provider)) {
                if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
                    throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                }
            }
//        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
            if (userMapper.existsByAccountId(dto.getAccountId())) {
                throw new RuntimeException("이미 사용 중인 아이디입니다.");
            }

            String encodedPassword;
            if ("KAKAO".equals(provider)) {
                encodedPassword = "kakao"; // 그냥 더미로 넣는 비밀번호
            } else {
                encodedPassword = passwordEncoder.encode(dto.getPassword());
            }
            //String encodedPassword = passwordEncoder.encode(dto.getPassword());

            UserVO user = UserVO.builder()
                    .name(dto.getName())
                    .accountId(dto.getAccountId())
                    .email(dto.getEmail())
                    .password(encodedPassword)
                    .provider(Provider.valueOf(provider))
                    .createdAt(LocalDateTime.now())
                    .build();
            userMapper.insertUser(user);

            log.debug("User inserted: {}", user);  // ✅ 추가된 로그

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
            log.debug("UserInfo inserted: {}", userInfo);  // ✅ 추가된 로그

        }
        catch (Exception e)
        {
            log.error("111111 회원가입 중 예외 발생: {}", e.getMessage(), e);  // 예외 전체 로그 출력
            throw e; // 예외 다시 던짐 (트랜잭션 롤백 등 유지)
        }


    }

    public boolean isAccountIdDuplicate(String accountId) {
        return userMapper.existsByAccountId(accountId);
    }
}

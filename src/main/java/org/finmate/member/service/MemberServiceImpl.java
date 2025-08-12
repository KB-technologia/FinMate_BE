package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.email.domain.EmailAuthVO;
import org.finmate.email.mapper.EmailAuthMapper;
import org.finmate.exception.NotFoundException;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.domain.UserVO;
import org.finmate.member.domain.enums.Gender;
import org.finmate.member.domain.enums.Provider;
import org.finmate.member.dto.ChangePasswordRequestDTO;
import org.finmate.member.dto.FindAccountIdResponseDTO;
import org.finmate.member.dto.SignupRequestDTO;
import org.finmate.member.mapper.UserInfoMapper;
import org.finmate.member.mapper.UserMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final EmailAuthMapper emailAuthMapper;
    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public FindAccountIdResponseDTO findAccountIdByUuid(final String uuid) {
        String email = emailAuthMapper.findEmailByVerifiedUuid(uuid);
        //TODO: 예외처리
        if (email == null) throw new NotFoundException("해당 유저 없음");

        // TODO: 예외처리
        UserVO user = userMapper.findAccountIdByEmail(email);
        if (user == null) throw new NotFoundException("해당 유저 없음");
        return FindAccountIdResponseDTO.from(user);
    }

    @Override
    public void verifyUser(final String uuid, final String accountId) {
        String email = emailAuthMapper.findEmailByVerifiedUuid(uuid);
        //TODO: 401예외처리
        if (email == null) throw new RuntimeException();

        boolean existsByAccountIdAndEmail = userMapper.existsByAccountIdAndEmail(accountId, email);

        //TODO: 401예외처리
        if (!existsByAccountIdAndEmail) {
            throw new RuntimeException();
        }
    }

    @Override
    public void resetPassword(final ChangePasswordRequestDTO dto) {
        String accountId = dto.getAccountId();
        String uuid = dto.getUuid();
        String newPassword = dto.getNewPassword();

        //TODO: 예외처리
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

    @Transactional
    @Override
    public void withdraw(final Long userId) {
        if (userMapper.findById(userId) == null) {
            throw new NotFoundException("ID: " + userId + "는 존재하지 않는 사용자입니다.");
        }
        try {
            userMapper.deletePortfolioByUserId(userId);
            userMapper.deleteUserAttendanceByUserId(userId);
            userMapper.deleteUserInfoByUserId(userId);
            userMapper.deleteFavoriteByUserId(userId);

            int deletedRows = userMapper.deleteUserById(userId);

            if (deletedRows == 0) {
                throw new RuntimeException("회원 정보가 정상적으로 삭제되지 않았습니다. (ID: " + userId + ")");
            }

        } catch (DataAccessException e) {
            log.error("회원 탈퇴 DB 처리 중 예외 발생. User ID: {}", userId, e);
            throw new RuntimeException("회원 탈퇴 처리 중 문제가 발생했습니다.", e);
        }
    }

    @Transactional
    @Override
    public void signup(final SignupRequestDTO dto) {

        try {
            log.info("[MemberService] 전달받은 provider: {}", dto.getProvider());

            Provider provider = dto.getProvider() != null ? Provider.valueOf(dto.getProvider().toUpperCase()) : Provider.LOCAL;

            if (userMapper.existsByAccountId(dto.getAccountId())) {
                throw new RuntimeException("이미 사용 중인 아이디입니다.");
            }

            String encodedPassword;
            switch (provider) {
                case LOCAL:
                    if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
                        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                    }
                    encodedPassword = passwordEncoder.encode(dto.getPassword());
                    break;

                case KAKAO:
                    encodedPassword = "kakao"; //더미 비밀번호
                    break;

                default:
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
                    .userLevel(1)
                    .characterTicket(0)
                    .updatedAt(LocalDateTime.now())
                    .build();
            userInfoMapper.insertUserInfo(userInfo);
            log.debug("UserInfo inserted: {}", userInfo);

        } catch (RuntimeException e) {
            //TODO: 예외처리 통일
            log.error("회원가입 처리 중 예외 발생: {}", e.getMessage(), e);
            throw new RuntimeException("회원가입 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
        }


    }

    @Override
    public boolean isAccountIdDuplicate(String accountId) {
        //TODO: 예외처리
        return userMapper.existsByAccountId(accountId);
    }
}

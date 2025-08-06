package org.finmate.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.attendance.domain.UserAttendanceVO;
import org.finmate.attendance.mapper.UserAttendanceMapper;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.domain.UserVO;
import org.finmate.security.dto.AuthResultDTO;
import org.finmate.security.dto.UserLoginInfoDTO;
import org.finmate.security.util.JsonResponse;
import org.finmate.security.util.JwtProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProcessor jwtProcessor;

    private final UserAttendanceMapper userAttendanceMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        UserVO userVO = customUser.getUser();

        String token = jwtProcessor.generateToken(userVO.getAccountId());
        UserLoginInfoDTO userInfo = UserLoginInfoDTO.of(userVO);

        UserAttendanceVO attendanceVO = userAttendanceMapper.getAttendanceByUserId(userVO.getId());
        boolean rewardClaimed = attendanceVO.getRewardClaimed();
        int consecutiveDays = attendanceVO.getConsecutiveDays();
        AuthResultDTO result = new AuthResultDTO(token, userInfo, false, rewardClaimed, consecutiveDays);

        JsonResponse.send(response,result);
    }
}

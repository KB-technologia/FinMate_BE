package org.finmate.member.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.dto.LevelRequestDTO;
import org.finmate.member.dto.LevelResponseDTO;
import org.finmate.member.dto.UserInfoDTO;
import org.finmate.member.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@RequiredArgsConstructor
@Log4j2
public class LevelServiceImpl implements LevelService{

    private final UserInfoMapper userInfoMapper;

    @Override
    public LevelResponseDTO getLevel(Long userId) {

        UserInfoDTO userInfoDTO = UserInfoDTO.from(userInfoMapper.getUserInfoById(userId));

        return LevelResponseDTO
                .builder()
                .currentLevel(userInfoDTO.getUserLevel())
                .totalExp(userInfoDTO.getExp())
                .characterTicketAdded(false)
                .profileSummary(userInfoDTO.getProfileSummary())
                .characterTicket(userInfoDTO.getCharacterTicket()).build();
    }

    @Override
    public LevelResponseDTO processLevelUp(Long userId, Integer gainedExp) {

        UserInfoDTO userInfoDTO = UserInfoDTO.from(userInfoMapper.getUserInfoById(userId));

        if(gainedExp == null) {
            throw new IllegalArgumentException("경험치(exp) 값은 필수입니다!");
        }

        int maxLevel = 30;
        int totalExp = userInfoDTO.getExp() + gainedExp;

        // 레벨업 조건 확인 | 경험치 1000 획득마다 레벨 업 (추후 조정 가능)
        int newLevel = totalExp / 1000;
        if (newLevel > maxLevel) newLevel = maxLevel;

        // 기존 사용자의 값
        int beforeLevel    = userInfoDTO.getUserLevel();
        int oldTickets     = userInfoDTO.getCharacterTicket();

        // 레벨별 캐릭터 변경권 횟수
        int totalTicketBefore = beforeLevel / 5;
        int totalTicketAfter  = newLevel  / 5;

        // 새로 추가할 티켓 수
        int addTicketCount = totalTicketAfter - totalTicketBefore;
        if (addTicketCount < 0) addTicketCount = 0;

        // 최종 보유 티켓 수
        int newTicketCount = oldTickets + addTicketCount;

        // 지급 여부
        boolean characterTicketAdded = (addTicketCount > 0);


        // UserInfo DB에 저장
        userInfoDTO.setExp(totalExp);
        userInfoDTO.setUserLevel(newLevel);
        userInfoDTO.setCharacterTicket(newTicketCount);
        userInfoMapper.insertUserInfo(userInfoDTO.toVO());

        // LevelResponseDTO 반환
        return LevelResponseDTO
                .builder()
                .currentLevel(newLevel)
                .totalExp(totalExp)
                .characterTicketAdded(characterTicketAdded)
                .characterTicket(newTicketCount)
                .build();
    }
}

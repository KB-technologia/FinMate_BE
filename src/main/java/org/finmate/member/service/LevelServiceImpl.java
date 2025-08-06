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
        if(userInfoDTO == null) {
            // user_id로 select가 안 된 상태
            log.info("userInfoDTO = {}", userInfoDTO);
            throw new RuntimeException("userInfoDTO is null!");
        }
        if(userInfoDTO.getUserLevel() == null) {
            // 매핑은 됐으나 userLevel이 null
            log.info("userInfoDTO = {}", userInfoDTO);
            throw new RuntimeException("userLevel is null!");
        }
        return LevelResponseDTO
                .builder()
                .currentLevel(userInfoDTO.getUserLevel())
                .totalExp(userInfoDTO.getUserLevel())
                .characterTicketAdded(false)
                .characterTicket(userInfoDTO.getCharacterTicket()).build();
    }

    @Override
    public LevelResponseDTO processLevelUp(Long userId, int gainedExp) {

        UserInfoDTO userInfoDTO = UserInfoDTO.from(userInfoMapper.getUserInfoById(userId));
        if(userInfoDTO == null) {
            // user_id로 select가 안 된 상태
            log.info("userInfoDTO = {}", userInfoDTO);
            throw new RuntimeException("userInfoDTO is null!");
        }
        if(userInfoDTO.getUserLevel() == null) {
            // 매핑은 됐으나 userLevel이 null
            log.info("userInfoDTO = {}", userInfoDTO);
            throw new RuntimeException("userLevel is null!");
        }
        int maxLevel = 30;
        int totalExp = userInfoDTO.getExp() + gainedExp;

        // 레벨업 조건 확인 | 경험치 1000 획득마다 레벨 업 (추후 조정 가능)
        int newLevel = totalExp / 1000;
        if (newLevel > maxLevel) newLevel = maxLevel;

        int remainExp = totalExp % 1000;

        // 캐릭터권 지급 횟수 (예: 5, 10, 15...)
        int oldTicketCount = userInfoDTO.getCharacterTicket();
        int addTicketCount = (newLevel / 5) - (userInfoDTO.getUserLevel() / 5);
        if (addTicketCount < 0) addTicketCount = 0;

        int newTicketCount = oldTicketCount + addTicketCount;
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

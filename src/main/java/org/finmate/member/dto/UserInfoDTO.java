package org.finmate.member.dto;

import lombok.*;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.domain.enums.SpeedTag;
import org.finmate.member.domain.enums.StrategyTag;
import org.finmate.member.domain.enums.ValueTag;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {

    private Long id; // PK
    private Long user_id; // 사용자 id
    private Long animal_id; // 동물 캐릭터 id

    private Integer exp; // 경험치
    private String profile_summary; // 사용자 성향 요약

    private Integer adventure_score; // 모험 성향 = 위험 감수도
    private ValueTag valueTag; // 가치관 = 투자 목적
    private SpeedTag speedTag; // 속도 = 투자기간
    private StrategyTag strategyTag; // 운/전략 = 투자 전략
    private Integer financeScore; // 재정 체력 = 리스크 허용도

    private LocalDateTime updatedAt;


    /**
     * VO -> DTO
     */
    public static UserInfoDTO toDTO(UserInfoVO vo){
        return UserInfoDTO.builder()
                .id(vo.getId())
                .user_id(vo.getUser_id())
                .animal_id(vo.getAnimal_id())
                .exp(vo.getExp())
                .profile_summary(vo.getProfile_summary())
                .adventure_score(vo.getAdventure_score())
                .valueTag(vo.getValueTag())
                .speedTag(vo.getSpeedTag())
                .strategyTag(vo.getStrategyTag())
                .strategyTag(vo.getStrategyTag())
                .financeScore(vo.getFinanceScore())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }
}

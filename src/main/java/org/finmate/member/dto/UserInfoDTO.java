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
    private Long userId; // 사용자 id
    private Long animalId; // 동물 캐릭터 id

    private Integer exp; // 경험치
    private String profileSummary; // 사용자 성향 요약

    private Double adventureScore; // 모험 성향 = 위험 감수도
    private ValueTag valueTag; // 가치관 = 투자 목적
    private SpeedTag speedTag; // 속도 = 투자기간
    private StrategyTag strategyTag; // 운/전략 = 투자 전략
    private Double financeScore; // 재정 체력 = 리스크 허용도

    private LocalDateTime updatedAt;


    /**
     * VO -> DTO
     */
    public static UserInfoDTO toDTO(UserInfoVO vo){
        return UserInfoDTO.builder()
                .id(vo.getId())
                .userId(vo.getUserId())
                .animalId(vo.getAnimalId())
                .exp(vo.getExp())
                .profileSummary(vo.getProfileSummary())
                .adventureScore(vo.getAdventureScore())
                .valueTag(vo.getValueTag())
                .speedTag(vo.getSpeedTag())
                .strategyTag(vo.getStrategyTag())
                .financeScore(vo.getFinanceScore())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    /**
     * DTO -> VO
     */
    public UserInfoVO toVO(){
        return UserInfoVO
                .builder()
                .id(id)
                .userId(userId)
                .animalId(animalId)
                .exp(exp)
                .profileSummary(profileSummary)
                .adventureScore(adventureScore)
                .valueTag(valueTag)
                .speedTag(speedTag)
                .strategyTag(strategyTag)
                .financeScore(financeScore)
                .updatedAt(updatedAt)
                .build();
    }
}

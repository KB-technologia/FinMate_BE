package org.finmate.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.enums.SpeedTag;
import org.finmate.member.domain.enums.StrategyTag;
import org.finmate.member.domain.enums.ValueTag;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoVO {

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
}

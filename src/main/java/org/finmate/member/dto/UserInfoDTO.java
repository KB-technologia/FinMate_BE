package org.finmate.member.dto;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "사용자 인포 PK")
    private Long id; // PK

    @ApiModelProperty(value = "사용자 ID")
    private Long userId; // 사용자 id

    @ApiModelProperty(value = "동물 캐릭터 ID")
    private Long animalId; // 동물 캐릭터 id

    @ApiModelProperty(value = "경험치")
    private Integer exp; // 경험치

    @ApiModelProperty(value = "사용자 투자 성향 요약")
    private String profileSummary; // 사용자 성향 요약

    @ApiModelProperty(value = "모험 성향 = 위험 감수도")
    private Double adventureScore; // 모험 성향 = 위
    // 험 감수도
    @ApiModelProperty(value = "가치관 = 투자 목적")
    private ValueTag valueTag; // 가치관 = 투자 목적

    @ApiModelProperty(value = "속도 = 투자기간")
    private SpeedTag speedTag; // 속도 = 투자기간

    @ApiModelProperty(value = "운/전략 = 투자 전략")
    private StrategyTag strategyTag; // 운/전략 = 투자 전략

    @ApiModelProperty(value = "재정 체력 = 리스크 허용도")
    private Double financeScore; // 재정 체력 = 리스크 허용도

    @ApiModelProperty(value = "수정일")
    private LocalDateTime updatedAt;


    /**
     * VO -> DTO
     */
    public static UserInfoDTO from(UserInfoVO vo){
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

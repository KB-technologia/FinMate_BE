package org.finmate.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.domain.enums.Gender;
import org.finmate.member.domain.enums.SpeedTag;
import org.finmate.member.domain.enums.StrategyTag;
import org.finmate.member.domain.enums.ValueTag;

import java.time.LocalDate;
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

    @ApiModelProperty(value = "사용자 레벨")
    private Integer userLevel;
    @ApiModelProperty(value = "캐릭터 변경권")
    private Integer characterTicket;

    @ApiModelProperty(value = "수정일")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "생년월일")
    private LocalDate birth;

    @ApiModelProperty(value = "성별 (MALE / FEMALE)")
    private Gender gender;

    @ApiModelProperty(value = "결혼 여부")
    private Boolean isMarried;

    @ApiModelProperty(value = "직업 유무")
    private Boolean hasJob;

    @ApiModelProperty(value = "대중교통 이용 여부")
    private Boolean usesPublicTransport;

    @ApiModelProperty(value = "운동 습관 여부")
    private Boolean doesExercise;

    @ApiModelProperty(value = "자주 여행을 다니는지 여부")
    private Boolean travelsFrequently;

    @ApiModelProperty(value = "자녀 유무")
    private Boolean hasChildren;

    @ApiModelProperty(value = "자가 주택 소유 여부")
    private Boolean hasHouse;

    @ApiModelProperty(value = "중소기업 재직 여부")
    private Boolean employedAtSme;

    @ApiModelProperty(value = "소액 대출 이용 여부")
    private Boolean usesMicroloan;


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
                .userLevel(vo.getUserLevel())
                .characterTicket(vo.getCharacterTicket())
                .updatedAt(vo.getUpdatedAt())

                .gender(vo.getGender())
                .isMarried(vo.getIsMarried())
                .hasJob(vo.getHasJob())
                .usesPublicTransport(vo.getUsesPublicTransport())
                .doesExercise(vo.getDoesExercise())
                .travelsFrequently(vo.getTravelsFrequently())
                .hasChildren(vo.getHasChildren())
                .hasHouse(vo.getHasHouse())
                .employedAtSme(vo.getEmployedAtSme())
                .usesMicroloan(vo.getUsesMicroloan())

                .build();
    }

    /**
     * DTO -> VO
     */
    public UserInfoVO toVO(){
        return UserInfoVO.builder()
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
                .userLevel(userLevel)
                .characterTicket(characterTicket)
                .updatedAt(updatedAt)

                .gender(gender)
                .isMarried(isMarried)
                .hasJob(hasJob)
                .usesPublicTransport(usesPublicTransport)
                .doesExercise(doesExercise)
                .travelsFrequently(travelsFrequently)
                .hasChildren(hasChildren)
                .hasHouse(hasHouse)
                .employedAtSme(employedAtSme)
                .usesMicroloan(usesMicroloan)

                .build();
    }
}

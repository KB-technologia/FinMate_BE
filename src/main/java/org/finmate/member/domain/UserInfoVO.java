package org.finmate.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.enums.*;

import java.time.LocalDate;
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

    private LocalDate birth; // 생년월일
    private Gender gender; // 성별

    private MaritalStatus maritalStatus; //결혼 유무
    private Boolean hasJob; // 직업 유무
    private Boolean usesPublicTransport; // 대중교통 이용 유무
    private Boolean exercises; // 운동 여부
    private AnniversaryFrequency anniversaryFrequency; // 기념일 챙기는 빈도
    private TravelFrequency travelsFrequently; // 여행 다니는 빈도
    private Integer numberOfChildren; // 자녀 수
    private HousingType housingType; // 주거 형태
    private Boolean worksAtSme; // 중소기업 재직 중인지
    private Boolean usesMicroloan; // 미소금융 대출 유무

    private LocalDateTime updatedAt;
}

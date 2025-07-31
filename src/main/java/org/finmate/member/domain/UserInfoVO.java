package org.finmate.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

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

    private Boolean isMarried; //결혼 유무
    private Boolean hasJob; // 직업 유무
    private Boolean usesPublicTransport; // 대중교통 이용 유무
    private Boolean doesExercise; // 운동 여부
    private Boolean travelsFrequently; // 여행 다니는지
    private Boolean hasChildren; // 자녀 유무
    private Boolean hasHouse; // 주거 형태
    private Boolean employedAtSme; // 중소기업 재직 중인지
    private Boolean usesMicroloan; // 미소금융 대출 유무

    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        String result = "";
        result += "나이: " + Period.between(birth, LocalDate.now()).getYears() + "\n";
        result += "성별: " + gender.name() + "\n";
        result += (hasJob ? "직업 있음" : "무직") + "\n";
        result += (usesPublicTransport ? "자주 대중교통 이용" : "대중교통 이용 안함") + "\n";
        result += (doesExercise ? "자주 운동함\n" : "");
        result += (travelsFrequently ? "자주 여행함\n" : "");
        result += (hasHouse ? "주택 보유" : "주택 없음") + "\n";
        result += (isMarried ? "기혼" : "미혼") + "\n";
        result += (hasChildren ? "자녀 있음" : "자녀 없음") + "\n";
        result += (employedAtSme ? "중소기업 재직중\n" : "");
        result += (usesMicroloan ? "미소 금융 사용중\n" : "");
        result += "투자 성격 : " + profileSummary + "\n";
        return result;
    }
}

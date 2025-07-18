package org.finmate.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioVO {
    private Long id;                      // 재무포트폴리오 ID (PK)
    private Long userId;                  // 사용자 ID (FK)

    private Integer age;                  // 나이
    private Double totalAssets;       // 자산 총액 (DECIMAL(15,2))
    private Double annualIncome;      // 연 소득 (DECIMAL(10,2))

    private String investmentProfile;     // 투자 성향

    private Double cashRatio;         // 현금/예금 비율
    private Double bondRatio;         // 채권 비율
    private Double equityRatio;       // 주식/펀드 비율
    private Double otherRatio;        // 기타 비율
}

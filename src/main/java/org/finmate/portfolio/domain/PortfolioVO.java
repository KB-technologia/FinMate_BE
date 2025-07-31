package org.finmate.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioVO {
    private Long id;                      // 재무포트폴리오 ID (PK)
    private Long userId;                  // 사용자 ID (FK)

    private Double totalAssets;       // 자산 총액 (DECIMAL(15,2))

    private InvestmentProfile investmentProfile;     // 투자 성향

    private Double cash;
    private Double deposit;
    private Double savings;
    private Double bond;
    private Double fund;
    private Double stock;
    private Double other;

    private LocalDateTime createdAt;

}

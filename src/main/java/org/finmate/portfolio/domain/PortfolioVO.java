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
    private Long id;
    private Long userId;
    private Double totalAssets;
    private Double cash;
    private Double deposit;
    private Double savings;
    private Double bond;
    private Double fund;
    private Double stock;
    private Double other;
    private LocalDateTime createdAt;

}

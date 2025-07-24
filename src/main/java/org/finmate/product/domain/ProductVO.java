package org.finmate.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private Long id;
    private String name;
    private String bankName;
    private ProductType productType;
    private Integer riskLevel;
    private Double expectedReturn;
    private Long minAmount;
    private Long maxAmount;
    private Integer minTerm;
    private Integer maxTerm;
    private String url;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer adventureScore;
    private String valueTag;
    private String speedTag;
    private String strategyTag;
    private Integer minFinanceScore;

    private DepositVO deposit;
    private SavingsVO savings;
    private FundVO fund;
    private ProductRateVO productRate;

}

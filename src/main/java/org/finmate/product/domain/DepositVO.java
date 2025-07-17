package org.finmate.product.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositVO {
    private Long id;
    private Long productId;
    private Double interestRate;
    private Boolean isFlexible;
    private Double earlyWithdrawalPenalty;
    private InterestType interestType;
    private Double baseRate;
    private Double bonusRate;
    private CompoundingPeriod compoundingPeriod;

    // product 1:1
    private ProductVO product;
}

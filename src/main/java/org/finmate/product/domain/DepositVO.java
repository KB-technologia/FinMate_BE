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
    private Boolean isFlexible;
    private Double earlyWithdrawalPenalty;
    private InterestType interestType;
    private Integer defaultTermMonths;
    private Double bonusRate;
    private CompoundingPeriod compoundingPeriod;

}

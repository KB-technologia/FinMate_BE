package org.finmate.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.enums.Gender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsVO {
    private Long id;
    private Long productId;
    private Boolean isFlexible;
    private PaymentCycle paymentCycle;
    private Long maxMonthlyPayment;
    private Double earlyWithdrawalPenalty;
    private InterestType interestType;
    private Integer defaultTermMonths;
    private Double bonusRate;
    private CompoundingPeriod compoundingPeriod;

    private Gender gender;
    private Boolean isMarried;
    private Boolean hasJob;
    private Boolean usesPublicTransport;
    private Boolean travelsFrequently;
    private Boolean doesExercise;
    private Boolean hasChildren;
    private Boolean hasHouse;
    private Boolean employedAtSme;
    private Boolean usesMicroloan;

}

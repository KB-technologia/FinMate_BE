package org.finmate.product.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundVO {
    private Long id;
    private Long productId;
    private FundType fundType;
    private String manager;
    private LocalDate inceptionDate;
    private Double initialNav;
    private Double nav;
    private Long aum;
    private LocalDate baseDate;
    private Double expenseRatio;
    private Integer redemptionPeriod;
    private Integer riskGrade;
    private String productClassCode;
    private String associationCode;
}

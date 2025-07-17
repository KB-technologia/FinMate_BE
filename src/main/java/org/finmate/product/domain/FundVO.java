package org.finmate.product.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

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
    private Double nav;
    private Long aum;
    private Double expenseRatio;
    private Double sharpeRatio;
    private String regions;
    private String sectors;
    private Integer redemptionPeriod;
    private Integer riskGrade;
    private Double returnRate1m;
    private Double returnRate3m;
    private Double returnRate6m;

    private ProductVO product;
}

package org.finmate.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRateVO {
    private Long id;
    private Long productId;
    private Double returnRate1m;
    private Double returnRate3m;
    private Double returnRate6m;
    private Double returnRate12m;
    private Double returnRate24m;
    private Double returnRate36m;
    private Double returnRate60m;
}

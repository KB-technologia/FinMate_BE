package org.finmate.product.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "금융 상품 비교 결과 DTO")
public class ProductComparisonResultDTO {
}
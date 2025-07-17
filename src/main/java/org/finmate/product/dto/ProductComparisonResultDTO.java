package org.finmate.product.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "첫 번째 금융 상품")
    private ProductDTO<?> product1;

    @ApiModelProperty(value = "두 번째 금융 상품")
    private ProductDTO<?> product2;

    @ApiModelProperty(value = "비교 결과 설명")
    private String comparisonResult;
}
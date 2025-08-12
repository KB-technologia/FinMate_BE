package org.finmate.portfolio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "유저에게 입력받는 DTO")
public class PortfolioRequestDTO {
    @ApiModelProperty(value = "현금")
    private Double cash;

    @ApiModelProperty(value = "기타 자산")
    private Double other;
}
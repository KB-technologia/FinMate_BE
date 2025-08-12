package org.finmate.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "금융 상품 필터링 DTO")
public class ProductFilterDTO {
    @ApiModelProperty(value = "검색 키워드", example = "퍼스트")
    private String query;

    @ApiModelProperty(value = "상품 타입", example = "DEPOSIT", allowableValues = "DEPOSIT,SAVINGS,FUND")
    private List<String> productType;

    @ApiModelProperty(value = "은행명 목록", example = "국민은행")
    private List<String> bankName;

    @ApiModelProperty(value = "펀드 종류 목록", example = "ETF")
    private List<String> fundType;

    @ApiModelProperty(value = "정렬 방식", example = "YIELD_DESC",
            allowableValues = "RECOMMENDED,YIELD_DESC,BASE_RATE_DESC")
    private String sortType = "YIELD_DESC";
}

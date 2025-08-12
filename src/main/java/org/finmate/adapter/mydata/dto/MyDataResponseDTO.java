package org.finmate.adapter.mydata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.product.domain.ProductType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "사용자가 가진 금융 상품 목록 DTO")
public class MyDataResponseDTO {

    @ApiModelProperty(value = "마이데이터 응답 코드")
    @JsonProperty("rsp_code")
    private String rspCode;

    @ApiModelProperty(value = "마이데이터 응답 메세지")
    @JsonProperty("rsp_msg")
    private String rspMsg;

    @ApiModelProperty(value = "사용자의 금융 상품 리스트")
    @JsonProperty("registered_list")
    private List<MyDataProductDTO> registered_list;

    public PortfolioDTO toPortfolioDTO() {
        Map<String, Double> balances = new HashMap<>();

        for (MyDataProductDTO product : registered_list) {
            String type = product.getProductType();
            double amount = product.getBalanceAmount();

            balances.put(type, balances.getOrDefault(type, 0.0) + amount);
        }

        return PortfolioDTO.builder()
                .deposit(balances.getOrDefault("DEPOSIT", 0.0))
                .savings(balances.getOrDefault("SAVINGS", 0.0))
                .fund(balances.getOrDefault("FUND", 0.0))
                .stock(balances.getOrDefault("STOCK", 0.0))
                .bond(balances.getOrDefault("BOND", 0.0))
                .build();
    }
}

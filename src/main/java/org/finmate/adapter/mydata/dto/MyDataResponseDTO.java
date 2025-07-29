package org.finmate.adapter.mydata.dto;

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
public class MyDataResponseDTO {
    private String rspCode;
    private String rspMsg;

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

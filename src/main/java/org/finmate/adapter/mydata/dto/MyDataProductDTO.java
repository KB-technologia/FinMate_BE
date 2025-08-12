package org.finmate.adapter.mydata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyDataProductDTO {
    @JsonProperty("prod_name")
    private String productName;

    @JsonProperty("prod_type")
    private String productType;

    @JsonProperty("account_num_masked")
    private String accountNumMasked;

    @JsonProperty("balance_amt")
    private Double balanceAmount;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("fund_type")
    private String fundType;

    @JsonProperty("risk_level")
    private String riskLevel;

    @JsonProperty("start_date")
    private Date startDate;

    @JsonProperty("end_date")
    private Date endDate;

    @JsonProperty("expected_return")
    private String expectedReturn;

    private Date joinedAt;
}

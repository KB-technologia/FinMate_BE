package org.finmate.adapter.mydata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyDataProductDTO {

    private String productName;

    private String productType;

    private String accountNumMasked;

    private Double balanceAmount;

    private Date joinedAt;
}

package org.finmate.portfolio.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.portfolio.domain.PortfolioVO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "재무 포트폴리오 DTO")
public class PortfolioDTO {

    @ApiModelProperty(value = "재무 포트폴리오 ID")
    private Long id;

    @ApiModelProperty(value = "사용자 ID(FK)")
    private Long userId;

    @ApiModelProperty(value = "사용자 나이")
    private Integer age;

    @ApiModelProperty(value = "사용자 자산 총액", example="50000000.0")
    private Double totalAssets;

    @ApiModelProperty(value = "사용자 연 소득", example="10000000.0")
    private Double annualIncome;

    @ApiModelProperty(value = "사용자 투자 성향", example="안정형")
    private String investmentProfile;

    @ApiModelProperty(value = "현금,예금 비율", example="10.0")
    private Double cashRatio;

    @ApiModelProperty(value = "채권 비율", example="20.0")
    private Double bondRatio;

    @ApiModelProperty(value = "주식,펀드 비율", example="40.0")
    private Double equityRatio;

    @ApiModelProperty(value = "기타 비율", example="30.0")
    private Double otherRatio;

    public static PortfolioDTO of(PortfolioVO vo) {
        return vo == null ? null : PortfolioDTO.builder()
                .id(vo.getId())
                .userId(vo.getUserId())
                .age(vo.getAge())
                .totalAssets(vo.getTotalAssets())
                .annualIncome(vo.getAnnualIncome())
                .investmentProfile(vo.getInvestmentProfile())
                .cashRatio(vo.getCashRatio())
                .bondRatio(vo.getBondRatio())
                .equityRatio(vo.getEquityRatio())
                .otherRatio(vo.getOtherRatio())
                .build();
    }
    public PortfolioVO toVO() {
        return PortfolioVO.builder()
                .userId(userId)
                .age(age)
                .totalAssets(totalAssets)
                .annualIncome(annualIncome)
                .investmentProfile(investmentProfile)
                .cashRatio(cashRatio)
                .bondRatio(bondRatio)
                .equityRatio(equityRatio)
                .otherRatio(otherRatio)
                .build();
    }
}

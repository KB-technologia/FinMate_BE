package org.finmate.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.portfolio.domain.PortfolioVO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {
    private Long id;
    private Long userId;
    private Integer age;
    private Double totalAssets;
    private Double annualIncome;
    private String investmentProfile;
    private Double cashRatio;
    private Double bondRatio;
    private Double equityRatio;
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
                .id(id)
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

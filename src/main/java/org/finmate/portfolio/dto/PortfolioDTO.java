package org.finmate.portfolio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.portfolio.domain.InvestmentProfile;
import org.finmate.portfolio.domain.PortfolioVO;

import java.time.LocalDateTime;

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

    @ApiModelProperty(value = "사용자 자산 총액", example="50000000.0")
    private Double totalAssets;


    @ApiModelProperty(value = "사용자 투자 성향", example = "CONSERVATIVE")
    private InvestmentProfile investmentProfile;

    @ApiModelProperty(value = "현금", example = "10000000.0")
    private Double cash;

    @ApiModelProperty(value = "예금", example = "15000000.0")
    private Double deposit;

    @ApiModelProperty(value = "적금", example = "15000000.0")
    private Double savings;

    @ApiModelProperty(value = "채권", example = "20000000.0")
    private Double bond;

    @ApiModelProperty(value = "펀드", example = "25000000.0")
    private Double fund;

    @ApiModelProperty(value = "주식", example = "30000000.0")
    private Double stock;

    @ApiModelProperty(value = "기타", example = "5000000.0")
    private Double other;

    @ApiModelProperty(value = "생성/수정 시각", example = "2024-07-28T10:15:30")
    private LocalDateTime createdAt;

    public static PortfolioDTO from(PortfolioVO vo) {
        return vo == null ? null : PortfolioDTO.builder()
                .id(vo.getId())
                .userId(vo.getUserId())
                .totalAssets(vo.getTotalAssets())
                .investmentProfile(vo.getInvestmentProfile())
                .cash(vo.getCash())
                .deposit(vo.getDeposit())
                .savings(vo.getSavings())
                .bond(vo.getBond())
                .fund(vo.getFund())
                .stock(vo.getStock())
                .other(vo.getOther())
                .createdAt(vo.getCreatedAt())
                .build();
    }

    public PortfolioVO toVO() {
        return PortfolioVO.builder()
                .id(id)
                .userId(userId)
                .totalAssets(totalAssets)
                .investmentProfile(investmentProfile)
                .cash(cash)
                .deposit(deposit)
                .savings(savings)
                .bond(bond)
                .fund(fund)
                .stock(stock)
                .other(other)
                .build();
    }

    /**
     * InvestmentProfile(투자성향) 계산 & 저장된 자산의 총합을 구하는 메서드 (로직 수정 필요)
     */
    public void update() {
        this.totalAssets = this.deposit + this.savings + this.bond + this.fund + this.stock + this.cash + this.other;
        double riskAssets = (this.stock) + (this.fund) + 0.5 * this.other;
        double safeAssets = this.bond + this.cash + 0.5 * this.other;

        double riskAssetsRatio = totalAssets > 0 ? (riskAssets / totalAssets) : 0;
        double safeAssetsRatio = totalAssets > 0 ? (safeAssets / totalAssets) : 0;

        if (riskAssetsRatio >= 0.6 && safeAssetsRatio <= 0.4) this.investmentProfile = InvestmentProfile.AGGRESSIVE;
        else if (riskAssetsRatio >= 0.4 && riskAssetsRatio < 0.6 && safeAssetsRatio <= 0.2) this.investmentProfile = InvestmentProfile.DYNAMIC;
        else if (riskAssetsRatio >= 0.3 && riskAssetsRatio <= 0.5 && safeAssetsRatio <= 0.4) this.investmentProfile = InvestmentProfile.BALANCED;
        else if (riskAssetsRatio >= 0.2 && riskAssetsRatio < 0.4 && safeAssetsRatio >= 0.4) this.investmentProfile = InvestmentProfile.CAUTIOUS;
        else this.investmentProfile = InvestmentProfile.CONSERVATIVE;
    }
}

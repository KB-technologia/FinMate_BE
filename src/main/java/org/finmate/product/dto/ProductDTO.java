package org.finmate.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.product.domain.ProductType;
import org.finmate.product.domain.ProductVO;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
@ApiModel(description = "금융 상품 DTO")
public class ProductDTO<T> {
    @ApiModelProperty(value = "금융 상품 ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "상품명")
    private String name;

    @ApiModelProperty(value = "은행명")
    private String bankName;

    @ApiModelProperty(value = "금융 상품 타입(예금, 적금, 펀드)")
    private ProductType productType;

    @ApiModelProperty(value = "위험도")
    private Integer riskLevel;

    @ApiModelProperty(value = "예상수익률")
    private Double expectedReturn;

    @ApiModelProperty(value = "최소가입금액")
    private Long minAmount;

    @ApiModelProperty(value = "최대가입금액")
    private Long maxAmount;

    @ApiModelProperty(value = "권장/만기 기간")
    private Integer term;

    @ApiModelProperty(value = "등록일")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "수정일")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "위험도(모험 성향)")
    private Integer adventureScore;

    @ApiModelProperty(value = "투자 목표 (생존, 안정, 고수익) (가치관)")
    private String valueTag;

    @ApiModelProperty(value = "기간 (빠름, 중간, 느림) (속도)")
    private String speedTag;

    @ApiModelProperty(value = "투자 방식(전략 타입)")
    private String strategyTag;

    @ApiModelProperty(value = "최소 소득/손실 감내도 요구치(최소 재정 체력)")
    private Integer minFinanceScore;

    @ApiModelProperty(value = "타입별 금융 상품 데이터")
    private T detail;

    public static ProductDTO<?> from(ProductVO vo) {
        var builder = builder()
                .id(vo.getId())
                .name(vo.getName())
                .bankName(vo.getBankName())
                .productType(vo.getProductType())
                .riskLevel(vo.getRiskLevel())
                .expectedReturn(vo.getExpectedReturn())
                .minAmount(vo.getMinAmount())
                .maxAmount(vo.getMaxAmount())
                .term(vo.getTerm())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .adventureScore(vo.getAdventureScore())
                .valueTag(vo.getValueTag())
                .speedTag(vo.getSpeedTag())
                .strategyTag(vo.getStrategyTag())
                .minFinanceScore(vo.getMinFinanceScore());

        switch (vo.getProductType()) {
            case DEPOSIT -> {
                log.info(1);
                return builder.detail(vo.getDeposit()).build();
            }
            case SAVINGS -> {
                log.info(vo.getSavings());
                return builder.detail(vo.getSavings()).build();
            }
            case FUND -> {
                log.info(vo.getFund());
                return builder.detail(vo.getFund()).build();
            }
            default -> throw new IllegalArgumentException("Unknown type");
        }
    }
}

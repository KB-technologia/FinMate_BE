package org.finmate.mypage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.enums.SpeedTag;
import org.finmate.member.domain.enums.StrategyTag;
import org.finmate.member.domain.enums.ValueTag;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "사용자 스탯 요청", description = "등록된 사용자 스탯 정보 DTO")
public class UserStatResponseDTO {
    @ApiModelProperty(value = "모험 성향(위험 감수도)", example = "0")
    private Double adventureScore;
    @ApiModelProperty(value = "가치관(투자 목적)", example = "SURVIVAL")
    private ValueTag valueTag;
    @ApiModelProperty(value = "속도(투자기간)", example = "SLOW")
    private SpeedTag speedTag;
    @ApiModelProperty(value = "운/전략(투자 전략)", example = "LUCK")
    private StrategyTag strategyTag;
    @ApiModelProperty(value = "재정 체력(리스크 허용도)", example = "2")
    private Double financeScore;
}

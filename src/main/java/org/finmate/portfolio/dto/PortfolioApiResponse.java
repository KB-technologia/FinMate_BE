package org.finmate.portfolio.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "재무 포트폴리오 응답 DTO")
public class PortfolioApiResponse<T> {
    @ApiModelProperty(value = "재무 포트폴리오 API 응답 메시지")
    private String message;

    @ApiModelProperty(value = "재무 포트폴리오 API 응답 데이터")
    private T data;
}

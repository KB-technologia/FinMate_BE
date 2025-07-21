package org.finmate.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "리뷰 등록/삭제에 관한 반환 DTO")
public class ReviewIdResponse {
    @ApiModelProperty(value = "등록되거나 삭제된 리뷰 ID")
    private Long id;
}
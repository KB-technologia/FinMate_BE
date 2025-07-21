package org.finmate.product.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDTO {

    @ApiModelProperty(value = "즐겨찾기 PK")
    private Long id;

    @ApiModelProperty(value = "사용자 ID")
    private Long userId;

    @ApiModelProperty(value = "즐겨찾기한 상품")
    private List<ProductDTO<?>> productDTOList;
}

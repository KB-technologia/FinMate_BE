package org.finmate.product.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.product.domain.FavoriteVO;

import java.util.List;
import java.util.stream.Collectors;

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

    public static FavoriteDTO from(FavoriteVO vo) {
        List<ProductDTO<?>> dtoList = vo.getProductVOList().stream()
                .map(pvo -> (ProductDTO<?>) ProductDTO.from(pvo))
                .collect(Collectors.toList());

        return FavoriteDTO.builder()
                .id(vo.getId())
                .userId(vo.getUserId())
                .productDTOList(dtoList)
                .build();
    }

}

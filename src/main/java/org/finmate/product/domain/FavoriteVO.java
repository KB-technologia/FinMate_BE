package org.finmate.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.finmate.product.dto.ProductDTO;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteVO {

    private Long id;

    private Long userId;

    private List<ProductVO> productVOList;
}

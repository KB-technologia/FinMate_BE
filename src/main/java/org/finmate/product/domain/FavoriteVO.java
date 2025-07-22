package org.finmate.product.domain;

import lombok.*;
import org.finmate.product.dto.ProductDTO;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteVO {

    private Long id;

    private Long userId;

    private List<ProductVO> productVOList;

}

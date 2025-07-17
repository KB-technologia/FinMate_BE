package org.finmate.product.mapper;

import org.finmate.product.domain.ProductVO;

public interface ProductMapper {
    ProductVO getProductDetail(Long id);
}

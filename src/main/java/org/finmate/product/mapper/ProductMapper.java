package org.finmate.product.mapper;

import org.finmate.product.domain.ProductVO;
import org.finmate.product.dto.ProductFilterDTO;

import java.util.List;

public interface ProductMapper {
    List<ProductVO> getAllProducts();

    ProductVO getProductDetail(Long id);

    List<ProductVO> getFilteredProductsByType(ProductFilterDTO filter);
}

package org.finmate.product.service;

import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;

public interface ProductService {
    ProductDTO<?> getProductDetail(Long id);
    ProductComparisonResultDTO compareProducts(Long id1, Long id2);
}

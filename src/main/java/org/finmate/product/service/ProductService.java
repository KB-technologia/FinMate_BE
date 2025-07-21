package org.finmate.product.service;

import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.dto.ProductFilterDTO;

import java.util.List;

public interface ProductService {
    ProductDTO<?> getProductDetail(Long id);
  
    List<ProductDTO<?>> getAllProducts();
  
    ProductComparisonResultDTO compareProducts(Long id1, Long id2);

    List<ProductDTO<?>> getFilteredProducts(ProductFilterDTO filter);
}

package org.finmate.product.service;

import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.dto.ProductReviewDTO;

import java.util.List;

public interface ProductService {
    ProductDTO<?> getProductDetail(Long id);
  
    List<ProductDTO<?>> getAllProducts();
  
    ProductComparisonResultDTO compareProducts(Long id1, Long id2);

    List<ProductReviewDTO> getProductReviews(Long id);

    Long insertProductReview(ProductReviewDTO productReviewDTO);

    Long deleteProductReview(Long id, Long userId);
}

package org.finmate.product.service;

import org.finmate.member.domain.CustomUser;
import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.dto.ProductFilterDTO;
import org.finmate.product.dto.ProductReviewDTO;

import java.util.List;

public interface ProductService {
    ProductDTO<?> getProductDetail(Long id);
  
    List<ProductDTO<?>> getAllProducts();
  
    ProductComparisonResultDTO compareProducts(Long id1, Long id2, CustomUser user);

    List<ProductDTO<?>> getFilteredProducts(ProductFilterDTO filter);

    List<ProductReviewDTO> getProductReviews(Long id);

    List<ProductReviewDTO> getMyReviews(Long userId);

    List<ProductReviewDTO> getMyReviewsByType(Long userId, String productType);

    Long insertProductReview(ProductReviewDTO productReviewDTO, Long productId, Long userId);

    Long deleteProductReview(Long id, Long userId);

    // 사용자 맞춤 추천 상품
    List<ProductDTO<?>> getCustomizedProducts(Long userId);
}

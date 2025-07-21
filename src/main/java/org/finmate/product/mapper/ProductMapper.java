package org.finmate.product.mapper;

import org.finmate.product.domain.ProductReviewVO;
import org.finmate.product.domain.ProductVO;

import java.util.List;
import java.util.Optional;

public interface ProductMapper {
    List<ProductVO> getAllProducts();

    ProductVO getProductDetail(Long id);

    List<ProductReviewVO> getProductReviewByProductId(Long productId);

    int insertProductReview(ProductReviewVO productReviewVO);

    int deleteProductReview(Long productId, Long userId);
}

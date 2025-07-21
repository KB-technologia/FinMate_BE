package org.finmate.product.mapper;

import org.apache.ibatis.annotations.Param;
import org.finmate.product.domain.ProductReviewVO;
import org.finmate.product.domain.ProductVO;

import java.util.List;
import java.util.Optional;

public interface ProductMapper {
    List<ProductVO> getAllProducts();

    Optional<ProductVO> getProductDetail(Long id);

    List<ProductReviewVO> getProductReviewByProductId(Long productId);

    int insertProductReview(ProductReviewVO productReviewVO);

    int deleteProductReview(
            @Param("productId") Long productId,
            @Param("userId") Long userId);
}

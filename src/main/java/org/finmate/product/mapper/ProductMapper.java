package org.finmate.product.mapper;
import org.finmate.product.domain.FavoriteVO;

import org.apache.ibatis.annotations.Param;
import org.finmate.product.domain.ProductReviewVO;
import org.finmate.product.domain.ProductVO;

import java.util.List;
import java.util.Optional;

public interface ProductMapper {
    List<ProductVO> getAllProducts();

    // 즐겨찾기 조회
    List<FavoriteVO> getFavorites(Long userId);

    // 즐겨찾기 등록
    int enrollFavorite(Long userId, Long productId);

    // 즐겨찾기 삭제
    int deleteFavorite(Long userId, Long productId);


    ProductVO getProductDetail(Long id);

    List<ProductReviewVO> getProductReviewByProductId(Long productId);

    int insertProductReview(ProductReviewVO productReviewVO);


    int deleteProductReview(
            @Param("productId") Long productId,
            @Param("userId") Long userId);

}


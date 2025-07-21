package org.finmate.product.mapper;

import org.finmate.product.domain.FavoriteVO;
import org.finmate.product.domain.ProductVO;

import java.util.List;

public interface ProductMapper {
    List<ProductVO> getAllProducts();

    ProductVO getProductDetail(Long id);

    // 즐겨찾기 조회
    List<FavoriteVO> getFavorites(Long userId);

    // 즐겨찾기 등록
    int enrollFavorite(Long userId, Long productId);

    // 즐겨찾기 삭제
    int deleteFavorite(Long favoriteId);
}


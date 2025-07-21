package org.finmate.product.service;

import org.finmate.product.dto.FavoriteDTO;

import java.util.List;

public interface FavoriteService {

    List<FavoriteDTO> getFavorites(Long userId);

    void enrollFavorite(Long userId, Long productId);

    void deleteFavorite(Long favoriteId);
}

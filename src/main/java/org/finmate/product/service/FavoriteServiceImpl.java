package org.finmate.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.product.dto.FavoriteDTO;
import org.finmate.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final ProductMapper productMapper;
    @Override
    public List<FavoriteDTO> getFavorites(Long userId) {
        return productMapper.getFavorites(userId)
                .stream()
                .map(FavoriteDTO::from).toList(); // Mapper는 FavoriteDTO 리스트 반환
    }

    @Transactional
    @Override
    public void enrollFavorite(Long userId, Long productId) {
        int inserted = productMapper.enrollFavorite(userId, productId);
        if (inserted == 0) {
            throw new IllegalStateException("이미 즐겨찾기된 상품입니다.");
        }
    }

    @Transactional
    @Override
    public void deleteFavorite(Long userId, Long productId) {
        int deleted = productMapper.deleteFavorite(userId, productId);
        if (deleted == 0) {
            throw new NoSuchElementException("삭제할 즐겨찾기를 찾을 수 없습니다.");
        }
    }
}

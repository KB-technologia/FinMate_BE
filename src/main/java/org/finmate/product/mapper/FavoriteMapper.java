package org.finmate.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.product.domain.ProductVO;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    List<ProductVO> getFavorites(Long userId);
}

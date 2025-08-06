package org.finmate.product.mapper;
import org.finmate.product.domain.*;

import org.apache.ibatis.annotations.Param;
import org.finmate.product.dto.ProductFilterDTO;

import java.util.List;
import java.util.Optional;

public interface ProductMapper {
    List<ProductVO> getAllProducts();

    // 즐겨찾기 조회
    List<FavoriteVO> getFavorites(Long userId);

    Long findProductIdByNameAndBankName(@Param("name") String name, @Param("bankName") String bankName);

    int insertProduct(ProductVO productVO);
    int updateProduct(ProductVO productVO);

    int insertDeposit(DepositVO depositVO);
    int updateDeposit(DepositVO depositVO);

    int insertSavings(SavingsVO savingsVO);
    int updateSavings(SavingsVO savingsVO);

    int insertFund(FundVO fundVO);
    int updateFund(FundVO fundVO);

    int insertProductRate(ProductRateVO productRateVO);
    int updateProductRate(ProductRateVO productRateVO);

    // 즐겨찾기 등록
    int enrollFavorite(@Param("userId") Long userId, @Param("productId") Long productId);

    // 즐겨찾기 삭제
    int deleteFavorite(@Param("userId") Long userId, @Param("productId") Long productId);

    ProductVO getProductDetail(Long id);

    List<ProductReviewVO> getProductReviewByProductId(Long productId);

    int insertProductReview(ProductReviewVO productReviewVO);


    List<ProductVO> getFilteredProductsByType(ProductFilterDTO filter);


    int deleteProductReview(
            @Param("productId") Long productId,
            @Param("userId") Long userId
    );

    // 상품 추천 (랜덤)
    List<ProductVO> getRandomProductRecommendation();

    List<ProductReviewVO> getProductReviewByUserId(@Param("userId") Long userId);

    List<ProductReviewVO> getProductReviewByUserIdAndType(
            @Param("userId") Long userId,
            @Param("productType") String productType
    );

    ProductReviewVO getProductReviewByProductIdAndUserId(
            @Param("productId") Long productId,
            @Param("userId") Long userId);
}


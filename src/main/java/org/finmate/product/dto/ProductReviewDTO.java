package org.finmate.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.product.domain.ProductReviewVO;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "금융 상품 리뷰 글 DTO")
public class ProductReviewDTO {
    @ApiModelProperty(value = "금융 상품 리뷰 ID")
    private Long id;
    @ApiModelProperty(value = "이 리뷰가 달린 금융 상품 ID")
    private Long productId;
    @ApiModelProperty(value = "리뷰를 작성한 유저 ID")
    private Long userId;
    @ApiModelProperty(value = "리뷰 별점")
    private Double rating;
    @ApiModelProperty(value = "이 금융 상품의 가입 편의성")
    private Double easeOfSignup;
    @ApiModelProperty(value = "리뷰 내용")
    private String content;
    @ApiModelProperty(value = "리뷰에 올릴 이미지 url")
    private String imageUrl;
    @ApiModelProperty(value = "작성자 이름")
    private String writer;
    @ApiModelProperty(value = "등록일")
    private LocalDateTime createdAt;

    public static ProductReviewDTO from(ProductReviewVO vo) {
        return builder()
                .id(vo.getId())
                .productId(vo.getProductId())
                .userId(vo.getUserId())
                .rating(vo.getRating())
                .easeOfSignup(vo.getEaseOfSignup())
                .content(vo.getContent())
                .imageUrl(vo.getImageUrl())
                .writer(vo.getWriter())
                .createdAt(vo.getCreatedAt())
                .build();
    }

    public static ProductReviewVO toVO(ProductReviewDTO dto) {
        return ProductReviewVO.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .userId(dto.getUserId())
                .rating(dto.getRating())
                .easeOfSignup(dto.getEaseOfSignup())
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .writer(dto.getWriter())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}

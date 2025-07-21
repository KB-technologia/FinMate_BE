package org.finmate.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewVO {
    private Long id;
    private Long productId;
    private Long userId;
    private Double rating;
    private Double easeOfSignup;
    private String content;
    private String imageUrl;
    private String writer;
    private LocalDateTime createdAt;
}
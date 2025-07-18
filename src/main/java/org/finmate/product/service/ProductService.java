package org.finmate.product.service;

import org.finmate.product.dto.ProductDTO;
import org.springframework.stereotype.Component;

public interface ProductService {
    ProductDTO getProductDetail(Long id);
}

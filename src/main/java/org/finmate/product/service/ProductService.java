package org.finmate.product.service;

import org.finmate.product.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getProductDetail(Long id);

    List<ProductDTO<?>> getAllProducts();
}

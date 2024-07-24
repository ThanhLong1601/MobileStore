package com.ex.mobilestore.service;

import com.ex.mobilestore.dto.ProductDetailDto;
import com.ex.mobilestore.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Page<ProductDto> getAllProducts(Pageable pageable);
    ProductDetailDto getProductById(Integer id);
    ProductDto addProduct(ProductDto product);
    ProductDto updateProduct(Integer id, ProductDto product);
    void deleteProduct(Integer id);
    Page<ProductDto> findAllByPrice(BigDecimal price, Pageable pageable);
    Page<ProductDto> findAllByCategoryName(String categoryName, Pageable pageable);
}

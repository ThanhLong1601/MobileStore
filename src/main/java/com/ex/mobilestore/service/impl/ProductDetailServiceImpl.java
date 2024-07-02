package com.ex.mobilestore.service.impl;

import com.ex.mobilestore.dto.ProductDetailDto;
import com.ex.mobilestore.entity.ProductDetailEntity;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.mapper.ProductDetailMapper;
import com.ex.mobilestore.repository.ProductDetailRepository;
import com.ex.mobilestore.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductDetailMapper productDetailMapper;

    @Autowired
    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository, ProductDetailMapper productDetailMapper) {
        this.productDetailRepository = productDetailRepository;
        this.productDetailMapper = productDetailMapper;
    }

    @Override
    public ProductDetailDto getProductDetailById(Integer id) {
        ProductDetailEntity productDetailEntity = productDetailRepository.findById(id)
                .orElseThrow(()-> new  NotFoundException(id));
        return productDetailMapper.toProductDetailDto(productDetailEntity);
    }
}

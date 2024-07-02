package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.ProductDetailDto;
import com.ex.mobilestore.entity.ProductDetailEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailMapper {

    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;

    @Autowired
    public ProductDetailMapper(ModelMapper modelMapper, ProductMapper productMapper) {
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
    }

    public ProductDetailDto toProductDetailDto(ProductDetailEntity productDetailEntity) {
        ProductDetailDto productDetailDto = modelMapper.map(productDetailEntity, ProductDetailDto.class);
        if (productDetailEntity.getProduct() != null) {
            productDetailDto.setProductDto(productMapper.toProductDTO(productDetailEntity.getProduct()));
        }
        return productDetailDto;
    }

    public ProductDetailEntity toProductDetailEntity(ProductDetailDto productDetailDto) {
        ProductDetailEntity productDetailEntity = modelMapper.map(productDetailDto, ProductDetailEntity.class);
        if (productDetailDto.getProductDto() != null) {
            productDetailEntity.setProduct(productMapper.toProductEntity(productDetailDto.getProductDto()));
        }
        return productDetailEntity;
    }
}

package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.ProductDto;
import com.ex.mobilestore.entity.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {
    private final ModelMapper modelMapper;
    private final ManufactureMapper manufactureMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper(ModelMapper modelMapper, ManufactureMapper manufactureMapper, CategoryMapper categoryMapper) {
        this.modelMapper = modelMapper;
        this.manufactureMapper = manufactureMapper;
        this.categoryMapper = categoryMapper;
    }

    public ProductDto toProductDTO(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }
        ProductDto productDto = modelMapper.map(productEntity, ProductDto.class);
        productDto.setManufacture(manufactureMapper.toManufactureDto(productEntity.getManufacture()));
        productDto.setCategory(categoryMapper.toCategoryDto(productEntity.getCategory()));
        return productDto;
    }

    public ProductEntity toProductEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
        productEntity.setManufacture(manufactureMapper.toManufactureEntity(productDto.getManufacture()));
        productEntity.setCategory(categoryMapper.toCategoryEntity(productDto.getCategory()));
        return productEntity;
    }
}

package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.CartProductDto;
import com.ex.mobilestore.entity.CartProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CartProductMapper {

    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;

    @Autowired
    public CartProductMapper(ModelMapper modelMapper, ProductMapper productMapper) {
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
    }

    public CartProductDto toCartProductDTO(CartProductEntity cartProductEntity) {
        if (cartProductEntity == null) {
            return null;
        }
        CartProductDto cartProductDto = modelMapper.map(cartProductEntity, CartProductDto.class);
        cartProductDto.setProduct(productMapper.toProductDTO(cartProductEntity.getProduct()));
        cartProductDto.setCartPrice(cartProductEntity.getCartPrice());
        cartProductDto.setQuantity(cartProductEntity.getQuantity());
        return cartProductDto;
    }

    public CartProductEntity toCartProductEntity(CartProductDto cartProductDto) {
        if (cartProductDto == null) {
            return null;
        }
        CartProductEntity cartProductEntity = modelMapper.map(cartProductDto, CartProductEntity.class);
        cartProductEntity.setProduct(productMapper.toProductEntity(cartProductDto.getProduct()));
        return cartProductEntity;
    }
}

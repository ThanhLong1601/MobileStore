package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.CartDto;
import com.ex.mobilestore.entity.CartEntity;
import com.ex.mobilestore.entity.CartProductEntity;
import com.ex.mobilestore.entity.OrderItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    private final ModelMapper modelMapper;
    private final CartProductMapper cartProductMapper;
    private final UserMapper userMapper;

    @Autowired
    public CartMapper(ModelMapper modelMapper, CartProductMapper cartProductMapper, UserMapper userMapper) {
        this.modelMapper = modelMapper;
        this.cartProductMapper = cartProductMapper;
        this.userMapper = userMapper;
    }

    public CartDto toCartDTO(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }
        CartDto cartDto = modelMapper.map(cartEntity, CartDto.class);
        cartDto.setUser(userMapper.toUserDto(cartEntity.getUser()));
        if (cartEntity.getCartProducts() != null) {
            cartDto.setCartProducts(cartEntity.getCartProducts().stream()
                    .map(cartProductMapper::toCartProductDTO)
                    .collect(Collectors.toSet()));
        }
        return cartDto;
    }

    public CartEntity toCartEntity(CartDto cartDto) {
        if (cartDto == null) {
            return null;
        }
        CartEntity cartEntity = modelMapper.map(cartDto, CartEntity.class);
        cartEntity.setUser(userMapper.toUserEntity(cartDto.getUser()));
        if (cartDto.getCartProducts() != null) {
            cartEntity.setCartProducts(cartDto.getCartProducts().stream()
                    .map(cartProductMapper::toCartProductEntity)
                    .collect(Collectors.toSet()));
        }
        return cartEntity;
    }

    public OrderItemEntity toOrderItemEntity(CartProductEntity cartProductEntity) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setProduct(cartProductEntity.getProduct());
        orderItemEntity.setQuantity(cartProductEntity.getQuantity());
        orderItemEntity.setPrice(cartProductEntity.getCartPrice());
        return orderItemEntity;
    }
}

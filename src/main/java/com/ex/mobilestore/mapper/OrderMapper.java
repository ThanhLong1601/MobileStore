package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.OrderDto;
import com.ex.mobilestore.entity.CartEntity;
import com.ex.mobilestore.entity.OrderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserMapper userMapper;

    @Autowired
    public OrderMapper(ModelMapper modelMapper, UserMapper userMapper, OrderItemMapper orderItemMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public OrderDto toOrderDTO(OrderEntity orderEntity) {
        OrderDto orderDto = modelMapper.map(orderEntity, OrderDto.class);
        if (orderEntity.getUser() != null) {
            orderDto.setUser(userMapper.toUserDto(orderEntity.getUser()));
        }
        if (orderEntity.getCart() != null) {
            orderDto.setCartId(orderEntity.getCart().getId());
        }
        if (orderEntity.getOrderItems() != null) {
            orderDto.setOrderItems(orderEntity.getOrderItems().stream()
                    .map(orderItemMapper::toOrderItemDTO)
                    .collect(Collectors.toSet()));
        }
        return orderDto;
    }

    public OrderEntity toOrderEntity(OrderDto orderDto) {
        OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
        if (orderDto.getUser() != null) {
            orderEntity.setUser(userMapper.toUserEntity(orderDto.getUser()));
        }
        if (orderDto.getCartId() != null) {
            CartEntity cartEntity = new CartEntity();
            cartEntity.setId(orderDto.getCartId());
            orderEntity.setCart(cartEntity);
        }
        if (orderDto.getOrderItems() != null) {
            orderEntity.setOrderItems(orderDto.getOrderItems().stream()
                    .map(orderItemMapper::toOrderItemEntity)
                    .collect(Collectors.toSet()));
        }
        return orderEntity;
    }
}

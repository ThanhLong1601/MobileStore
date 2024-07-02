package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.OrderItemDto;
import com.ex.mobilestore.entity.OrderItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;

    @Autowired
    public OrderItemMapper(ModelMapper modelMapper, ProductMapper productMapper) {
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
    }

    public OrderItemDto toOrderItemDTO(OrderItemEntity orderItemEntity) {
        if (orderItemEntity == null) {
            return null;
        }
        OrderItemDto orderItemDto = modelMapper.map(orderItemEntity, OrderItemDto.class);
        orderItemDto.setProduct(productMapper.toProductDTO(orderItemEntity.getProduct()));
        return orderItemDto;
    }

    public OrderItemEntity toOrderItemEntity(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }
        OrderItemEntity orderItemEntity = modelMapper.map(orderItemDto, OrderItemEntity.class);
        orderItemEntity.setProduct(productMapper.toProductEntity(orderItemDto.getProduct()));
        return orderItemEntity;
    }
}

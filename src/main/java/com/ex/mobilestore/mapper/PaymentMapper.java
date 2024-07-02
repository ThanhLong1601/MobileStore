package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.PaymentDto;
import com.ex.mobilestore.entity.PaymentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public PaymentMapper(ModelMapper modelMapper, OrderMapper orderMapper) {
        this.modelMapper = modelMapper;
        this.orderMapper = orderMapper;
    }

    public PaymentDto toPaymentDTO(PaymentEntity paymentEntity) {
        if (paymentEntity == null) {
            return null;
        }
        PaymentDto paymentDto = modelMapper.map(paymentEntity, PaymentDto.class);
        if (paymentEntity.getOrder() != null) {
            paymentDto.setOrder(orderMapper.toOrderDTO(paymentEntity.getOrder()));
        }
        return paymentDto;
    }

    public PaymentEntity toPaymentEntity(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        }
        PaymentEntity paymentEntity = modelMapper.map(paymentDto, PaymentEntity.class);
        if (paymentDto.getOrder() != null) {
            paymentEntity.setOrder(orderMapper.toOrderEntity(paymentDto.getOrder()));
        }
        return paymentEntity;
    }
}

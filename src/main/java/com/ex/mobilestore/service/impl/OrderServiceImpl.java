package com.ex.mobilestore.service.impl;

import com.ex.mobilestore.constant.CartStatus;
import com.ex.mobilestore.dto.OrderDto;
import com.ex.mobilestore.entity.CartEntity;
import com.ex.mobilestore.entity.OrderEntity;
import com.ex.mobilestore.entity.OrderItemEntity;
import com.ex.mobilestore.entity.UserEntity;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.mapper.CartMapper;
import com.ex.mobilestore.mapper.OrderMapper;
import com.ex.mobilestore.repository.CartRepository;
import com.ex.mobilestore.repository.OrderItemRepository;
import com.ex.mobilestore.repository.OrderRepository;
import com.ex.mobilestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper , CartRepository cartRepository, CartMapper cartMapper ) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderDTO)
                .toList();
    }

    @Override
    public OrderDto getOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderDTO)
                .orElseThrow(()-> new NotFoundException(id));
    }

    @Override
    public OrderDto placeOrder(Integer cartId, String recipientName, String recipientPhoneNumber, String shippingAddress) {

        CartEntity cartEntity = cartRepository.findById(cartId)
                .orElseThrow(()-> new NotFoundException(cartId));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(cartEntity.getUser());
        orderEntity.setCart(cartEntity);
        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setOrderStatus("ORDERED");
        orderEntity.setRecipientName(recipientName);
        orderEntity.setRecipientPhoneNumber(recipientPhoneNumber);
        orderEntity.setShippingAddress(shippingAddress);

        Set<OrderItemEntity> orderItems = cartEntity.getCartProducts().stream()
                .map(cartMapper::toOrderItemEntity)
                .peek(orderItemEntity -> orderItemEntity.setOrder(orderEntity))
                .collect(Collectors.toSet());
        orderEntity.setOrderItems(orderItems);

        OrderEntity saveOrder = orderRepository.save(orderEntity);

        cartEntity.setStatus(CartStatus.ORDERED);
        cartRepository.save(cartEntity);

        return orderMapper.toOrderDTO(saveOrder);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}

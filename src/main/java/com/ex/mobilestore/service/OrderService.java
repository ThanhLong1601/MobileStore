package com.ex.mobilestore.service;

import com.ex.mobilestore.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Integer id);
    OrderDto placeOrder(Integer cartId, String recipientName, String recipientPhoneNumber, String shippingAddress);
    void deleteOrder(Integer id);
}

package com.ex.mobilestore.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class OrderDto {
    private Integer id;
    private UserDto user;
    private Integer cartId;
    private LocalDate orderDate;
    private String orderStatus;
    private String recipientName;
    private String recipientPhoneNumber;
    private String shippingAddress;
    private Set<OrderItemDto> orderItems = new LinkedHashSet<>();
}

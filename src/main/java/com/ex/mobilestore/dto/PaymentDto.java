package com.ex.mobilestore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDto {
    private Integer id;
    private OrderDto order;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
}

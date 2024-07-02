package com.ex.mobilestore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Integer id;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal price;
}

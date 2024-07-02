package com.ex.mobilestore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductDto {
    private Integer id;
    private ProductDto product;
    private BigDecimal cartPrice;
    private Integer quantity;
}

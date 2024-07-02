package com.ex.mobilestore.dto;

import com.ex.mobilestore.constant.CartStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class CartDto {
    private Integer id;
    private UserDto user;
    private Set<CartProductDto> cartProducts = new LinkedHashSet<>();
    private BigDecimal totalProduct;
    private CartStatus status;
    private Date createdDate;
}

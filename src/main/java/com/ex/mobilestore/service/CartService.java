package com.ex.mobilestore.service;

import com.ex.mobilestore.dto.CartDto;

import java.util.List;

public interface CartService {
    List<CartDto> getAllCarts();
    CartDto getCartById(Integer cartId);
    CartDto removeProductFromCart(Integer productId);
    CartDto addProductToCart(Integer productId);
    void clearCart();
}
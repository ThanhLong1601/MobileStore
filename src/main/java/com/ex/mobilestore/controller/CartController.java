package com.ex.mobilestore.controller;

import com.ex.mobilestore.constant.AppConstant;
import com.ex.mobilestore.dto.CartDto;
import com.ex.mobilestore.dto.common.BaseResponse;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.service.CartService;
import com.ex.mobilestore.utils.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/listAll")
    public ResponseEntity<BaseResponse<List<CartDto>>> getAllCarts(){
        List<CartDto> carts = cartService.getAllCarts();
        return ResponseFactory.ok(carts);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<BaseResponse<CartDto>> removeProductFromCart(@PathVariable Integer productId){
        try {
            CartDto removeCart = cartService.removeProductFromCart(productId);
            return ResponseFactory.ok(removeCart);
        }catch (NotFoundException e){
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }

    @PostMapping("/{productId}")
    public ResponseEntity<BaseResponse<CartDto>> addProductToCart(@PathVariable Integer productId) {
        try {
            CartDto cartDto = cartService.addProductToCart(productId);
            return ResponseFactory.ok(cartDto);
        } catch (NotFoundException e) {
            return ResponseFactory.error(null, AppConstant.ERROR_CODE, e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<BaseResponse<Void>> clearCart(){
        try {
            cartService.clearCart();
            return ResponseFactory.ok(null);
        }catch (NotFoundException e){
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }
}
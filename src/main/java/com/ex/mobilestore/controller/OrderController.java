package com.ex.mobilestore.controller;

import com.ex.mobilestore.constant.AppConstant;
import com.ex.mobilestore.dto.OrderDto;
import com.ex.mobilestore.dto.common.BaseResponse;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.service.OrderService;
import com.ex.mobilestore.utils.ResponseFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/listAll")
    public ResponseEntity<BaseResponse<List<OrderDto>>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseFactory.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<OrderDto>> getOrderById(@PathVariable Integer id) {
        try {
            OrderDto getOrderById = orderService.getOrderById(id);
            return ResponseFactory.ok(getOrderById);
        }catch (NotFoundException e){
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<BaseResponse<OrderDto>> placeOrder(@Valid @RequestBody OrderDto request) {
        try {
            OrderDto placedOrder = orderService.placeOrder(
                    request.getCartId(),
                    request.getRecipientName(),
                    request.getRecipientPhoneNumber(),
                    request.getShippingAddress()
            );
            return ResponseFactory.ok(placedOrder);
        } catch (NotFoundException e) {
            return ResponseFactory.error(null, AppConstant.ERROR_CODE, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteOrder(@PathVariable Integer id) {
        try {
            orderService.deleteOrder(id);
            return ResponseFactory.ok(null);
        }catch (NotFoundException e){
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }
}

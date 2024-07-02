package com.ex.mobilestore.controller;

import com.ex.mobilestore.constant.AppConstant;
import com.ex.mobilestore.dto.PaymentDto;
import com.ex.mobilestore.dto.common.BaseResponse;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.service.PaymentService;
import com.ex.mobilestore.utils.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/listAll")
    public ResponseEntity<BaseResponse<List<PaymentDto>>> getAllPayments() {
        List<PaymentDto> payments = paymentService.getAllPayments();
        return ResponseFactory.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PaymentDto>> getPaymentById(@PathVariable Integer id) {
        try {
            PaymentDto payment = paymentService.getPaymentById(id);
            return ResponseFactory.ok(payment);
        }catch (NotFoundException e) {
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }
}

package com.ex.mobilestore.service;

import com.ex.mobilestore.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> getAllPayments();
    PaymentDto getPaymentById(Integer id);
}

package com.ex.mobilestore.service.impl;

import com.ex.mobilestore.dto.PaymentDto;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.mapper.PaymentMapper;
import com.ex.mobilestore.repository.PaymentRepository;
import com.ex.mobilestore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toPaymentDTO)
                .toList();
    }

    @Override
    public PaymentDto getPaymentById(Integer id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toPaymentDTO)
                .orElseThrow(()-> new NotFoundException(id));
    }
}

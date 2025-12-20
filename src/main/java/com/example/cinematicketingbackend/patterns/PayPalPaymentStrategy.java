package com.example.cinematicketingbackend.patterns;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.dto.PayPalPaymentDto;

@Service
public class PayPalPaymentStrategy
        implements PaymentStrategyInterface<PayPalPaymentDto> {

    @Override
    public void pay(PayPalPaymentDto request) {
        // validate paypal email
    }
}
package com.example.cinematicketingbackend.service;

import com.example.cinematicketingbackend.patterns.PaymentStrategy;
import org.springframework.stereotype.Service;

@Service
public class PaymentServices {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment(double amount) {
        return paymentStrategy.pay(amount);
    }
}

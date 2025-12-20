package com.example.cinematicketingbackend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.cinematicketingbackend.patterns.*;
import com.example.cinematicketingbackend.dto.*;;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentFactory paymentFactory;

    public PaymentController(PaymentFactory paymentFactory) {
        this.paymentFactory = paymentFactory;
    }

    // ---------------- CASH ----------------
    @PostMapping("/cash")
    public Map<String, String> payCash(@RequestBody CashPaymentDto dto) {

        PaymentStrategyInterface<CashPaymentDto> strategy =
                paymentFactory.getStrategy("CASH");

        strategy.pay(dto);
        return Map.of("message", "Cash payment successful");
    }

    // ---------------- CARD ----------------
    @PostMapping("/card")
    public Map<String, String> payByCard(@RequestBody CreditCardPaymentDto dto) {

        PaymentStrategyInterface<CreditCardPaymentDto> strategy =
                paymentFactory.getStrategy("CARD");

        strategy.pay(dto);
        return Map.of("message", "Card payment successful");
    }

    // ---------------- PAYPAL ----------------
    @PostMapping("/paypal")
    public Map<String, String>payByPaypal(@RequestBody PayPalPaymentDto dto) {

        PaymentStrategyInterface<PayPalPaymentDto> strategy =
                paymentFactory.getStrategy("PAYPAL");

        strategy.pay(dto);
        return Map.of("message", "PayPal payment successful");
    }
}
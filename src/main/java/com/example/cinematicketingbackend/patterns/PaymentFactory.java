package com.example.cinematicketingbackend.patterns;

import org.springframework.stereotype.Component;
@Component
public class PaymentFactory {

    private final CashPaymentStrategy cash;
    private final CreditCardPaymentStrategy card;
    private final PayPalPaymentStrategy paypal;

    public PaymentFactory(
            CashPaymentStrategy cash,
            CreditCardPaymentStrategy card,
            PayPalPaymentStrategy paypal
    ) {
        this.cash = cash;
        this.card = card;
        this.paypal = paypal;
    }

    public PaymentStrategyInterface getStrategy(String type) {

        if ("CASH".equalsIgnoreCase(type)) return cash;
        if ("CARD".equalsIgnoreCase(type)) return card;
        if ("PAYPAL".equalsIgnoreCase(type)) return paypal;

        throw new IllegalArgumentException("Unsupported payment type");
    }
}
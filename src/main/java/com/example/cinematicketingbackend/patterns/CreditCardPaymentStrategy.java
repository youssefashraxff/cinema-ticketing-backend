package com.example.cinematicketingbackend.patterns;



import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.dto.CreditCardPaymentDto;

@Service
public class CreditCardPaymentStrategy
        implements PaymentStrategyInterface<CreditCardPaymentDto> {

    @Override
    public void pay(CreditCardPaymentDto request) {
        // validate card
        // charge card
    }
}

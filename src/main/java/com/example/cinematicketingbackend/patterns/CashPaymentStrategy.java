package com.example.cinematicketingbackend.patterns;
import com.example.cinematicketingbackend.dto.CashPaymentDto;


import org.springframework.stereotype.Service;

@Service
public class CashPaymentStrategy
        implements PaymentStrategyInterface<CashPaymentDto> {

    @Override
    public void pay(CashPaymentDto request) {
        // mark booking as PAY_ON_DELIVERY
    }
}

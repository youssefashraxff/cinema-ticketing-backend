package com.example.cinematicketingbackend.patterns;

public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid payment amount");
            return false;
        }

        System.out.println("Your Payment will processd when you come.\n Your total is: " + amount );
        return false;  //logically lesa he didn't pay(payment will be processed on his movie date)
    }
}

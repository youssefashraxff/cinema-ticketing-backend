package com.example.cinematicketingbackend.patterns;

public interface PaymentStrategy {
    boolean pay(double amount);
}

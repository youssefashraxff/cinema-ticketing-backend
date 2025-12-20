package com.example.cinematicketingbackend.patterns;

public interface PaymentStrategyInterface<T> {
    void pay(T request);
}

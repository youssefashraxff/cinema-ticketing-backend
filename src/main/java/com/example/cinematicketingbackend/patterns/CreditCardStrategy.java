package com.example.cinematicketingbackend.patterns;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreditCardStrategy implements PaymentStrategy {
    private String cardNumber;
    private String cvv;
    private String expiry;
    private double balance;

    public CreditCardStrategy(String cardNumber, String cvv, String expiryDate, double balance) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiry = expiryDate;
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        boolean valid = true;

        if (cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d{16}")) {
            System.out.println("Invalid card number. Must be exactly 16 digits.");
            valid = false;
        }

        if (cvv == null || cvv.length() != 3 || !cvv.matches("\\d{3}")) {
            System.out.println("Invalid CVV. Must be exactly 3 digits.");
            valid = false;
        }

        if (!isValidExpiry()) {
            System.out.println("Invalid expiry date. Format must be MM/yy and date must not be expired.");
            valid = false;
        }

        if (amount <= 0) {
            System.out.println("Invalid payment amount. Must be greater than 0.");
            valid = false;
        }

        if (balance < amount) {
            System.out.println("Insufficient balance on card. Current balance: " + balance);
            valid = false;
        }

        if (!valid) {
            System.out.println("Payment failed.");
            return false;
        }

        balance -= amount;
        System.out.println("Payment successful!");
        System.out.println("Remaining balance: " + balance);
        return true;
    }

    private boolean isValidExpiry() {
        if (expiry == null || expiry.isEmpty()) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expiryDate = YearMonth.parse(expiry, formatter);
            return expiryDate.isAfter(YearMonth.now()) || expiryDate.equals(YearMonth.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

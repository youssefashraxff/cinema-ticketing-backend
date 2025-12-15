package com.example.cinematicketingbackend.patterns;

public class PayPalStrategy implements PaymentStrategy {
    private String email;
    private String password;
    private double balance;   // simulate PayPal wallet balance

    public PayPalStrategy(String email, String password, double balance) {
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public boolean pay(double amount) {

        if (!isAuthenticated()) {
            System.out.println("PayPal authentication failed");
            return false;
        }
        if (balance < amount) {
            System.out.println("Insufficient PayPal balance");
            return false;
        }
        balance -= amount;
        System.out.println("PayPal payment successful. Remaining balance: " + balance);
        return true;
    }

    private boolean isAuthenticated() {
        return email != null && email.contains("@")
                && password != null && password.length() >= 6;
    }
}

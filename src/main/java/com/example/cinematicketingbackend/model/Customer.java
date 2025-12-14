package com.example.cinematicketingbackend.model;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<String>bookingHistory;

    public Customer(String id, String username, String password, String email) {
        super(id, username, password, email, "customer");
        this.bookingHistory = new ArrayList<>();
    }

    public List<String> getBookingHistory() {
        return bookingHistory;
    }

}
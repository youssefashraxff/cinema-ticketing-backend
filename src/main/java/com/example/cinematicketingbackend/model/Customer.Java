package com.example.cinematicketingbackend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {
    private List<String> bookingHistory = new ArrayList<>();

    public Customer() {
        super();
        this.setRole("Customer");
    }
}
package com.example.cinematicketingbackend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    private String employeeId;

    public Admin() {
        super();
        this.setRole("Admin");
    }
}
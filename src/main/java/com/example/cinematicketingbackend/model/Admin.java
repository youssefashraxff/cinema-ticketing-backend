package com.example.cinematicketingbackend.model;

public class Admin extends User{
    private String employeeid;

    public Admin(String id, String username, String password, String email, String employeeid) {
        super(id, username, password, email, "admin");
        this.employeeid = employeeid;
    }


    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }
}

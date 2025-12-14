package com.example.cinematicketingbackend.model;

public class Admin extends User{
    private String permissions;
    private String employeeid;

    public Admin(String id, String username, String password, String email, String employeeid, String permissions) {
        super(id, username, password, email, "admin");
        this.employeeid = employeeid;
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }
}

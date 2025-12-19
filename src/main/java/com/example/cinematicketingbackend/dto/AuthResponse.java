package com.example.cinematicketingbackend.dto;

import com.example.cinematicketingbackend.model.User;

public class AuthResponse {
    private User user;
    private String token;

    public AuthResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    // getters
    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}

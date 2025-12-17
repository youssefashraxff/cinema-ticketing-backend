package com.example.cinematicketingbackend.service;

import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.repository.FacadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final FacadeRepository facade;

    public UserService(FacadeRepository facade) {
        this.facade = facade;
    }


    public User registerUser(User user) {

        boolean usernameExists = facade.users()
                .findByUsername(user.getUsername())
                .isPresent();

        if (usernameExists) {
            return null;
        }

        boolean emailExists = facade.users()
                .findAll()
                .stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Email already exists");
        }

        facade.users().save(user);
        return user;
    }

    public User login(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        User user = facade.users()
                .findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return user;
    }

    public User getUserById(String userId) {
        return facade.users()
                .findById(userId)
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return facade.users().findAll();
    }

    public User updateUser(User user) {
        facade.users().save(user);
        return user;
    }
}

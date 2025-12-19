package com.example.cinematicketingbackend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.cinematicketingbackend.dto.AuthResponse;
import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.repository.FacadeRepository;

@Service
public class UserService {

    private final FacadeRepository facade;

    public UserService(FacadeRepository facade) {
        this.facade = facade;
    }


    public AuthResponse registerUser(String email, String password , String username,String role) {

        boolean emailExists = facade.users()
                .findAll()
                .stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

        if (emailExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already exists");
        }
        int newUserId = facade.users()
                .findAll()
                .stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0) + 1;

        User user = new User(
                newUserId,
                username,
                password,
                email,
                role
        );
        String token = JwtService.generateToken(user);
        facade.users().save(user);
        return new AuthResponse(user, token);
    }

    public AuthResponse login(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        User user = facade.users()
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        String token = JwtService.generateToken(user);
        return new AuthResponse(user, token);
    }

    public User getUserById(int userId) {
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

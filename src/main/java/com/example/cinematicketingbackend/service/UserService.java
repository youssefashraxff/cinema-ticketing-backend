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


    public AuthResponse registerUser(String email, String password, String username) {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()
                || username == null || username.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email, username and password are required"
            );
        }

        boolean emailExists = facade.users()
                .findAll()
                .stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

        if (emailExists) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email already exists"
            );
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
                "USER"
        );

        facade.users().save(user);

        String token = JwtService.generateToken(user);
        return new AuthResponse(user, token);
    }

    public AuthResponse login(String email, String password) {

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email and password are required"
            );
        }

        User user = facade.users()
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Incorrect email or password"
                        )
                );

        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Incorrect email or password"
            );
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

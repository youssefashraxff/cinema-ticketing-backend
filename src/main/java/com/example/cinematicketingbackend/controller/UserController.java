package com.example.cinematicketingbackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cinematicketingbackend.dto.AuthResponse;
import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody Map<String, String> body) {
        return userService.registerUser(
                body.get("email"),
                body.get("password"),
                body.get("username")
        );
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody Map<String, String> body) {

        return userService.login(body.get("email"), body.get("password"));
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}

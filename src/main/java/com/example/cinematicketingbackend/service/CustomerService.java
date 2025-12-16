package com.example.cinematicketingbackend.service;
import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.patterns.UserFactory;
import java.util.ArrayList;
import java.util.List;

public class
CustomerService {

    private List<User> users = new ArrayList<>();
    private User loggedInUser;    //tracks the current logged-in user

    public User registerUser(String username, String password, String email) {
        User existing = findByUsername(username);
        if (existing != null) {
            System.out.println("Username already exists! Please login instead.");
            return null;
        }
        String role;
        if (username.toLowerCase().startsWith("admin_")) {
            role = "admin";
        } else {
            role = "customer";
        }

        User user = UserFactory.createUser(role, username, password, email);
        users.add(user);
        System.out.println("Registration successful for user: " + username + " with role: " + role);
        return user;
    }

    public User login(String username, String password) {
        User user = findByUsername(username);
        if (user == null) {
            System.out.println("Username not found! Please register first.");
            return null;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password. Try again.");
            return null;
        }

        loggedInUser = user;
        System.out.println("Login successful: " + username + " (" + user.getRole() + ")");
        return user;
    }

    public void logout() {
        loggedInUser = null;
        System.out.println("Logged out successfully.");
    }

    public void updateProfile(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                return;
            }
        }
        users.add(user);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    private User findByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }
}

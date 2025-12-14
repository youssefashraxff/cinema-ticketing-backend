package com.example.cinematicketingbackend.service;
import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.patterns.UserFactory;
import com.example.cinematicketingbackend.repository.CustomerRepository;
import java.util.List;

public class CustomerService {
    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public User registerUser(String username, String password, String email) {
        // Check if user exists
        User existing = repository.findByUsername(username);
        if (existing != null) {
            System.out.println("Username already exists! Please login instead.");
            return null;
        }
        // Determine role based on username
        String role;
        if (username.toLowerCase().startsWith("admin_")) {
            role = "admin";
        } else {
            role = "customer";
        }
        User user = UserFactory.createUser(role, username, password, email);
        repository.save(user);
        System.out.println("Registration successful for user: " + username + " with role: " + role);
        return user;
    }


    public User authenticate(String username, String password) {
        User user = repository.findByUsername(username);
        if (user == null) {
            System.out.println("Username not found! Please register first.");
            return null;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password. Try again.");
            return null;
        }
        System.out.println("Login successful: " + username + " (" + user.getRole() + ")");
        return user;
    }

    public void updateProfile(User user){
        List<User> users = repository.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                return;
            }
        }

        repository.save(user);
    }

}
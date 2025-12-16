//package com.example.cinematicketingbackend.repository;
//import com.example.cinematicketingbackend.model.User;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomerRepository {
//    private List<User> users = new ArrayList<>();
//
//    public void save(User user){
//        users.add(user);
//    }
//
//    public User findByUsername(String username){
//        return users.stream()
//                .filter(u -> u.getUsername().equals(username))
//                .findFirst()
//                .orElse(null);
//    }
//
//    public List<User> getUsers() {
//        return users;
//    }
//
//}
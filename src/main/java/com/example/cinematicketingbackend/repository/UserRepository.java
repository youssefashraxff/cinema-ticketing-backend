package com.example.cinematicketingbackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.cinematicketingbackend.model.User;
import com.fasterxml.jackson.core.type.TypeReference;

@Repository
public class UserRepository {

    private static final String FILE_PATH = "data/users.json";


    private final FileManager fileManager;

    public UserRepository() {
        this.fileManager = FileManager.getInstance();
    }

    private List<User> loadUsers() {
        try {
            List<User> data = fileManager.read(
                    FILE_PATH,
                    new TypeReference<List<User>>() {}
            );
            System.out.println("Data:"+data);
            return data != null ? data : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Data empty");
            return new ArrayList<>();
        }
    }

    private void saveUsers(List<User> users) {
        fileManager.write(FILE_PATH, users);
    }

    public List<User> findAll() {
        return new ArrayList<>(loadUsers());
    }

    public Optional<User> findById(int id) {
        return loadUsers().stream()
                .filter(u -> u.getId()==id)
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return loadUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public User save(User user) {
        List<User> users = loadUsers();
        System.out.println(users);
        users.removeIf(u -> u.getId()==(user.getId()));
        users.add(user);

        saveUsers(users);
        return user;
    }

    public void deleteById(int id) {
        List<User> users = loadUsers();
        users.removeIf(u -> u.getId()==id);
        saveUsers(users);
    }
}

package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            return data != null ? data : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveUsers(List<User> users) {
        fileManager.write(FILE_PATH, users);
    }

    public List<User> findAll() {
        return new ArrayList<>(loadUsers());
    }

    public Optional<User> findById(String id) {
        return loadUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return loadUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public User save(User user) {
        List<User> users = loadUsers();

        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);

        saveUsers(users);
        return user;
    }

    public void deleteById(String id) {
        List<User> users = loadUsers();
        users.removeIf(u -> u.getId().equals(id));
        saveUsers(users);
    }
}

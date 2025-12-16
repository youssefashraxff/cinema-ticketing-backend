package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.util.FileHandlerManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HallRepository {
    private static HallRepository instance;
    private List<Hall> halls;
    private final FileHandlerManager fileHandler;
    private final Path hallsFilePath;

    private HallRepository() {
        this.fileHandler = FileHandlerManager.getInstance();
        this.hallsFilePath = Paths.get("src/main/resources/data/halls.json");
        loadHallsFromJson();
    }

    public static synchronized HallRepository getInstance() {
        if (instance == null) {
            instance = new HallRepository();
        }
        return instance;
    }

    private void loadHallsFromJson() {
        try {
            // FIXED: Use TypeReference for proper deserialization
            halls = fileHandler.readList(hallsFilePath, new TypeReference<List<Hall>>() {});
        } catch (Exception e) {
            System.err.println("Error loading halls from file: " + e.getMessage());
            halls = new ArrayList<>();
        }
    }

    public List<Hall> getAllHalls() {
        return new ArrayList<>(halls); // Return copy to prevent external modification
    }

    public Optional<Hall> getHallById(int hallId) {
        return halls.stream()
                .filter(hall -> hall.getHallId() == hallId)
                .findFirst();
    }

    // FIXED: Save a single hall (add or update)
    public void save(Hall hall) {
        if (hall == null) {
            throw new IllegalArgumentException("Hall cannot be null");
        }
        
        // Remove existing hall with same ID if it exists
        halls.removeIf(h -> h.getHallId() == hall.getHallId());
        
        // Add the hall (new or updated)
        halls.add(hall);
        
        saveAllToFile();
    }

    // FIXED: Save all halls (merge, not replace)
    public void saveAll(List<Hall> hallsToSave) {
        if (hallsToSave == null || hallsToSave.isEmpty()) {
            return;
        }
        
        for (Hall hall : hallsToSave) {
            // Remove existing hall with same ID
            halls.removeIf(h -> h.getHallId() == hall.getHallId());
            // Add the hall
            halls.add(hall);
        }
        
        saveAllToFile();
    }
    
    // FIXED: Delete hall by ID
    public boolean deleteById(int hallId) {
        boolean removed = halls.removeIf(hall -> hall.getHallId() == hallId);
        if (removed) {
            saveAllToFile();
        }
        return removed;
    }
    
    // FIXED: Separate method for saving to file
    private void saveAllToFile() {
        try {
            fileHandler.write(hallsFilePath, halls);
        } catch (Exception e) {
            System.err.println("Error saving halls to file: " + e.getMessage());
            throw new RuntimeException("Failed to save halls", e);
        }
    }

    // Additional useful methods
    public boolean existsById(int hallId) {
        return halls.stream().anyMatch(hall -> hall.getHallId() == hallId);
    }
    
    public List<Hall> findByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Hall> result = new ArrayList<>();
        String normalizedStatus = status.trim().toUpperCase();
        for (Hall hall : halls) {
            if (normalizedStatus.equals(hall.getHallStatus())) {
                result.add(hall);
            }
        }
        return result;
    }
    
    public List<Hall> findByType(String hallType) {
        if (hallType == null || hallType.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Hall> result = new ArrayList<>();
        String normalizedType = hallType.trim().toUpperCase();
        for (Hall hall : halls) {
            if (normalizedType.equals(hall.getHallType())) {
                result.add(hall);
            }
        }
        return result;
    }
    
    public long count() {
        return halls.size();
    }
    
    public void clear() {
        halls.clear();
        saveAllToFile();
    }
}
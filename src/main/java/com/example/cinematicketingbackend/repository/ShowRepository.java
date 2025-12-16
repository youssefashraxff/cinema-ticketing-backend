package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.util.FileHandlerManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowRepository {
    private static ShowRepository instance;
    private List<Show> shows;
    private final FileHandlerManager fileHandler;
    private final Path showsFilePath;
    private final HallRepository hallRepository;

    private ShowRepository() {
        this.fileHandler = FileHandlerManager.getInstance();
        this.showsFilePath = Paths.get("src/main/resources/data/shows.json");
        this.hallRepository = HallRepository.getInstance();
        loadShowsFromJson();
    }

    public static synchronized ShowRepository getInstance() {
        if (instance == null) {
            instance = new ShowRepository();
        }
        return instance;
    }

    private void loadShowsFromJson() {
        try {
            // Read shows from JSON
            shows = fileHandler.readList(showsFilePath, new TypeReference<List<Show>>() {});
            
            // After loading, validate and fix hall references
            validateAndFixHallReferences();
        } catch (Exception e) {
            System.err.println("Error loading shows from file: " + e.getMessage());
            shows = new ArrayList<>();
        }
    }

    // FIXED: Validate that hall references are valid
    private void validateAndFixHallReferences() {
        List<Show> validShows = new ArrayList<>();
        
        for (Show show : shows) {
            if (show.getHall() != null) {
                // Get the actual hall from hallRepository
                Optional<Hall> actualHall = hallRepository.getHallById(show.getHall().getHallId());
                if (actualHall.isPresent()) {
                    // Replace with the hall from repository to avoid duplication
                    show.setHall(actualHall.get());
                    validShows.add(show);
                } else {
                    System.err.println("Warning: Show references non-existent hall ID: " + 
                                      show.getHall().getHallId() + ". Skipping this show.");
                }
            }
        }
        
        shows = validShows;
        saveAllToFile(); // Save the cleaned-up list
    }

    public List<Show> getAllShows() {
        return new ArrayList<>(shows);
    }

    public List<Show> findAll() {
        return new ArrayList<>(shows);
    }

    public Optional<Show> findById(String showKey) {
        return shows.stream()
                .filter(show -> generateShowKey(show).equals(showKey))
                .findFirst();
    }

    public List<Show> findByHallId(int hallId) {
        List<Show> result = new ArrayList<>();
        for (Show show : shows) {
            if (show.getHall() != null && show.getHall().getHallId() == hallId) {
                result.add(show);
            }
        }
        return result;
    }

    public List<Show> findByHall(Hall hall) {
        if (hall == null) {
            return new ArrayList<>();
        }
        return findByHallId(hall.getHallId());
    }

    // Generate unique key for a show (hallId + startTime)
    private String generateShowKey(Show show) {
        if (show.getHall() == null || show.getStartTime() == null) {
            return "";
        }
        return show.getHall().getHallId() + "_" + show.getStartTime();
    }

    private String generateShowKey(int hallId, String startTime) {
        return hallId + "_" + startTime;
    }

    // FIXED: Save a single show with proper hall reference validation
    public void save(Show show) {
        if (show == null || show.getHall() == null) {
            throw new IllegalArgumentException("Show and its hall cannot be null");
        }

        // Validate hall exists
        Optional<Hall> actualHall = hallRepository.getHallById(show.getHall().getHallId());
        if (!actualHall.isPresent()) {
            throw new IllegalArgumentException("Hall with ID " + show.getHall().getHallId() + " does not exist");
        }

        // Replace with the actual hall from repository
        show.setHall(actualHall.get());

        // Remove existing show with same hall and start time
        String showKey = generateShowKey(show);
        shows.removeIf(s -> generateShowKey(s).equals(showKey));
        
        // Add the new show
        shows.add(show);
        
        saveAllToFile();
    }

    // FIXED: Save all shows with proper validation
    public void saveAll(List<Show> showsToSave) {
        if (showsToSave == null || showsToSave.isEmpty()) {
            return;
        }

        for (Show show : showsToSave) {
            if (show.getHall() == null) {
                System.err.println("Warning: Show has null hall, skipping");
                continue;
            }

            // Validate hall exists
            Optional<Hall> actualHall = hallRepository.getHallById(show.getHall().getHallId());
            if (!actualHall.isPresent()) {
                System.err.println("Warning: Show references non-existent hall ID: " + 
                                 show.getHall().getHallId() + ". Skipping.");
                continue;
            }

            // Replace with actual hall
            show.setHall(actualHall.get());

            // Remove existing show with same key
            String showKey = generateShowKey(show);
            shows.removeIf(s -> generateShowKey(s).equals(showKey));
            
            // Add the show
            shows.add(show);
        }
        
        saveAllToFile();
    }

    private void saveAllToFile() {
        try {
            fileHandler.write(showsFilePath, shows);
        } catch (Exception e) {
            System.err.println("Error saving shows to file: " + e.getMessage());
            throw new RuntimeException("Failed to save shows", e);
        }
    }

    public boolean deleteByHallAndTime(int hallId, String startTime) {
        String showKey = generateShowKey(hallId, startTime);
        boolean removed = shows.removeIf(show -> generateShowKey(show).equals(showKey));
        if (removed) {
            saveAllToFile();
        }
        return removed;
    }

    public int deleteByHallId(int hallId) {
        int initialSize = shows.size();
        shows.removeIf(show -> show.getHall() != null && show.getHall().getHallId() == hallId);
        int removedCount = initialSize - shows.size();
        if (removedCount > 0) {
            saveAllToFile();
        }
        return removedCount;
    }

    public long count() {
        return shows.size();
    }

    public long countByHallId(int hallId) {
        return shows.stream()
                .filter(show -> show.getHall() != null && show.getHall().getHallId() == hallId)
                .count();
    }

    public boolean isHallAvailable(int hallId, String startTime, String endTime) {
        // Simplified availability check
        List<Show> hallShows = findByHallId(hallId);
        for (Show show : hallShows) {
            // Check for overlap
            if (show.getStartTime().compareTo(endTime) < 0 && 
                show.getFinishTime().compareTo(startTime) > 0) {
                return false;
            }
        }
        return true;
    }
}
package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.util.FileHandlerManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatRepository {
    private static SeatRepository instance;
    private List<Seat> seats;
    private final FileHandlerManager fileHandler;
    private final Path seatsFilePath;

    private SeatRepository() {
        this.fileHandler = FileHandlerManager.getInstance();
        this.seatsFilePath = Paths.get("src/main/resources/data/seats.json");
        loadSeatsFromJson();
    }

    public static synchronized SeatRepository getInstance() {
        if (instance == null) {
            instance = new SeatRepository();
        }
        return instance;
    }

    private void loadSeatsFromJson() {
        try {
            seats = fileHandler.readList(seatsFilePath, new TypeReference<List<Seat>>() {});
        } catch (Exception e) {
            System.err.println("Error loading seats from file: " + e.getMessage());
            seats = new ArrayList<>();
        }
    }

    public List<Seat> getAllSeats() {
        return new ArrayList<>(seats);
    }

    public List<Seat> findAll() {
        return new ArrayList<>(seats);
    }

    public Optional<Seat> findBySeatNumberAndHallId(int seatNumber, int hallId) {
        return seats.stream()
                .filter(seat -> seat.getSeatNumber() == seatNumber && seat.getHallid() == hallId)
                .findFirst();
    }

    public List<Seat> findByHallId(int hallId) {
        List<Seat> result = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.getHallid() == hallId) {
                result.add(seat);
            }
        }
        return result;
    }

    public List<Seat> findByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Seat> result = new ArrayList<>();
        String normalizedStatus = status.trim().toLowerCase();
        for (Seat seat : seats) {
            if (normalizedStatus.equals(seat.getStatus())) {
                result.add(seat);
            }
        }
        return result;
    }

    public List<Seat> findAvailableSeatsByHallId(int hallId) {
        List<Seat> result = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.getHallid() == hallId && "available".equals(seat.getStatus())) {
                result.add(seat);
            }
        }
        return result;
    }

    // Generate unique key for seat
    private String generateSeatKey(int hallId, int seatNumber) {
        return hallId + "_" + seatNumber;
    }

    private String generateSeatKey(Seat seat) {
        return seat.getHallid() + "_" + seat.getSeatNumber();
    }

    // Save a single seat
    public void save(Seat seat) {
        if (seat == null) {
            throw new IllegalArgumentException("Seat cannot be null");
        }

        // Generate unique key
        String seatKey = generateSeatKey(seat);
        
        // Remove existing seat with same hall and seat number
        seats.removeIf(s -> generateSeatKey(s).equals(seatKey));
        
        // Add the seat
        seats.add(seat);
        
        saveAllToFile();
    }

    // Save all seats properly
    public void saveAll(List<Seat> seatsToSave) {
        if (seatsToSave == null || seatsToSave.isEmpty()) {
            return;
        }

        for (Seat seat : seatsToSave) {
            // Remove existing seat with same key
            String seatKey = generateSeatKey(seat);
            seats.removeIf(s -> generateSeatKey(s).equals(seatKey));
            // Add the seat
            seats.add(seat);
        }
        
        saveAllToFile();
    }

    // Initialize seats for a hall
    public void initializeSeatsForHall(int hallId, int capacity) {
        // Remove existing seats for this hall
        seats.removeIf(seat -> seat.getHallid() == hallId);
        
        // Create new seats
        for (int i = 1; i <= capacity; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setHallid(hallId);
            seat.setStatus("available");
            seats.add(seat);
        }
        
        saveAllToFile();
    }

    private void saveAllToFile() {
        try {
            fileHandler.write(seatsFilePath, seats);
        } catch (Exception e) {
            System.err.println("Error saving seats to file: " + e.getMessage());
            throw new RuntimeException("Failed to save seats", e);
        }
    }

    public boolean updateSeatStatus(int hallId, int seatNumber, String status) {
        Optional<Seat> seatOpt = findBySeatNumberAndHallId(seatNumber, hallId);
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            seat.setStatus(status);
            saveAllToFile();
            return true;
        }
        return false;
    }

    public boolean deleteByHallId(int hallId) {
        boolean removed = seats.removeIf(seat -> seat.getHallid() == hallId);
        if (removed) {
            saveAllToFile();
        }
        return removed;
    }

    public long count() {
        return seats.size();
    }

    public long countByHallId(int hallId) {
        return seats.stream()
                .filter(seat -> seat.getHallid() == hallId)
                .count();
    }

    public long countByHallIdAndStatus(int hallId, String status) {
        String normalizedStatus = status != null ? status.trim().toLowerCase() : "";
        return seats.stream()
                .filter(seat -> seat.getHallid() == hallId && normalizedStatus.equals(seat.getStatus()))
                .count();
    }
}
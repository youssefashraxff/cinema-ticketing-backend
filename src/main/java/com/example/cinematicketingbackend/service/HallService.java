package com.example.cinematicketingbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.cinematicketingbackend.exception.HallInUseException;
import com.example.cinematicketingbackend.exception.InvalidHallStatusException;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.repository.HallRepository;

public class HallService {
    private HallRepository hallRepository;
    private ShowService showService;

    public HallService() {
        this.hallRepository = HallRepository.getInstance();
    }

    public void setShowService(ShowService showService) {
        this.showService = showService;
    }

    public Hall createHall(int capacity, String hallType, int seatPrice) {
        if (capacity <= 20) {
            throw new IllegalArgumentException("Hall capacity must be greater than 20");
        }

        // Validate hall type
        String normalizedType = hallType.toUpperCase().trim();
        if (!isValidHallType(normalizedType)) {
            throw new IllegalArgumentException(
                "Invalid hall type: " + hallType + ". Valid types: STANDARD, VIP, IMAX, 3D, PREMIUM");
        }

        // Validate seat price
        if (seatPrice <= 0) {
            throw new IllegalArgumentException("Seat price must be greater than 0");
        }

        // Generate new hall ID
        List<Hall> allHalls = hallRepository.getAllHalls();
        int newHallId = 1;
        if (!allHalls.isEmpty()) {
            newHallId = allHalls.stream()
                    .mapToInt(Hall::getHallId)
                    .max()
                    .orElse(0) + 1;
        }

        Hall hall = new Hall(newHallId, capacity, normalizedType);
        hall.setSeatPrice(seatPrice);
        
        // Save hall using repository
        hallRepository.save(hall);
        
        return hall;
    }

    private boolean isValidHallType(String hallType) {
        return hallType.equals("STANDARD") || 
               hallType.equals("VIP") || 
               hallType.equals("IMAX") || 
               hallType.equals("3D") || 
               hallType.equals("PREMIUM");
    }

    public void deleteHall(int hallId) {
        Optional<Hall> hallOpt = getHallById(hallId);
        if (!hallOpt.isPresent()) {
            throw new IllegalArgumentException("Hall with ID " + hallId + " does not exist");
        }

        Hall hall = hallOpt.get();

        // Check if hall has scheduled shows
        if (showService != null) {
            List<Show> hallShows = showService.getShowsByHall(hallId);
            if (hallShows != null && !hallShows.isEmpty()) {
                throw new HallInUseException(hallId);
            }
        }

        // Delete hall
        hallRepository.deleteById(hallId);
    }
   
    public Hall getHall(int hallId) {
        Optional<Hall> hallOpt = getHallById(hallId);
        return hallOpt.orElse(null);
    }
    
    private Optional<Hall> getHallById(int hallId) {
        return hallRepository.getHallById(hallId);
    }

    public List<Hall> getAllHalls() {
        return hallRepository.getAllHalls();
    }

    public List<Hall> getHallsByType(String hallType) {
        if (hallType == null || hallType.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String normalizedType = hallType.toUpperCase().trim();
        List<Hall> allHalls = hallRepository.getAllHalls();
        List<Hall> result = new ArrayList<>();
        
        for (Hall hall : allHalls) {
            if (hall.getHallType().equals(normalizedType)) {
                result.add(hall);
            }
        }
        return result;
    }

    public Hall updateHallStatus(int hallId, String newStatus) {
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new InvalidHallStatusException("Status cannot be null or empty");
        }

        String normalizedStatus = newStatus.toUpperCase().trim();
        if (!isValidHallStatus(normalizedStatus)) {
            throw new InvalidHallStatusException(
                    "Invalid status: " + newStatus + ". Valid statuses: ACTIVE, INACTIVE, MAINTENANCE, CLOSED");
        }

        Optional<Hall> hallOpt = getHallById(hallId);
        if (!hallOpt.isPresent()) {
            throw new InvalidHallStatusException("Hall with ID " + hallId + " not found");
        }

        Hall hall = hallOpt.get();

        // Validate status transition
        String currentStatus = hall.getHallStatus();
        if (currentStatus != null && !isValidStatusTransition(currentStatus, normalizedStatus)) {
            throw new InvalidHallStatusException(
                    "Invalid status transition from " + currentStatus + " to " + normalizedStatus);
        }

        hall.setHallStatus(normalizedStatus);
        
        // Save updated hall
        hallRepository.save(hall);
        return hall;
    }
   
    private boolean isValidHallStatus(String status) {
        return status.equals("ACTIVE") || 
               status.equals("INACTIVE") || 
               status.equals("MAINTENANCE") || 
               status.equals("CLOSED");
    }
   
    private boolean isValidStatusTransition(String fromStatus, String toStatus) {
        // Same status is always allowed
        if (fromStatus.equals(toStatus)) {
            return true;
        }

        // Define allowed transitions
        switch (fromStatus) {
            case "ACTIVE":
                // ACTIVE can transition to INACTIVE, MAINTENANCE, or CLOSED
                return toStatus.equals("INACTIVE") || toStatus.equals("MAINTENANCE") || toStatus.equals("CLOSED");
            case "INACTIVE":
                // INACTIVE can transition to ACTIVE or CLOSED
                return toStatus.equals("ACTIVE") || toStatus.equals("CLOSED");
            case "MAINTENANCE":
                // MAINTENANCE can transition to ACTIVE or CLOSED
                return toStatus.equals("ACTIVE") || toStatus.equals("CLOSED");
            case "CLOSED":
                // CLOSED can transition to ACTIVE (reopening) or MAINTENANCE
                return toStatus.equals("ACTIVE") || toStatus.equals("MAINTENANCE");
            default:
                return false;
        }
    }

    public List<Hall> getActiveHalls() {
        List<Hall> allHalls = hallRepository.getAllHalls();
        List<Hall> result = new ArrayList<>();
        
        for (Hall hall : allHalls) {
            if ("ACTIVE".equals(hall.getHallStatus())) {
                result.add(hall);
            }
        }
        return result;
    }

    public List<Hall> getHallsByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String normalizedStatus = status.toUpperCase().trim();
        if (!isValidHallStatus(normalizedStatus)) {
            return new ArrayList<>();
        }

        List<Hall> allHalls = hallRepository.getAllHalls();
        List<Hall> result = new ArrayList<>();
        
        for (Hall hall : allHalls) {
            if (normalizedStatus.equals(hall.getHallStatus())) {
                result.add(hall);
            }
        }
        return result;
    }

    public boolean isHallAvailable(int hallId) {
        Optional<Hall> hallOpt = getHallById(hallId);
        if (!hallOpt.isPresent()) {
            return false;
        }
        Hall hall = hallOpt.get();
        return "ACTIVE".equals(hall.getHallStatus());
    }
}
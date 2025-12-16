package com.example.cinematicketingbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.cinematicketingbackend.exception.HallInUseException;
import com.example.cinematicketingbackend.exception.InvalidHallStatusException;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Show;



public class HallService {
    private Map<Integer, Hall> halls;
    private int nextHallId;
    private ShowService showService;


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

        int newHallId = nextHallId++;
        Hall hall = new Hall(newHallId, capacity, normalizedType,seatPrice);
        halls.put(newHallId, hall);
        

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
        Hall hall = halls.get(hallId);
        if (hall == null) {
            System.out.println("Hall does not exist");
        }

        // Check if hall has scheduled shows
        if (showService != null) {
            List<Show> hallShows = showService.getShowsByHall(hallId);
            if (hallShows != null && !hallShows.isEmpty()) {
                throw new HallInUseException(hallId);
            }
        }

        halls.remove(hallId);
    }

   
    public Hall getHall(int hallId) {
        return halls.get(hallId);
    }

    public List<Hall> getAllHalls() {
        return new ArrayList<>(halls.values());
    }

  
    public List<Hall> getHallsByType(String hallType) {
        if (hallType == null || hallType.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String normalizedType = hallType.toUpperCase().trim();
        return halls.values().stream()
                .filter(hall -> hall.getHallType().equals(normalizedType))
                .collect(Collectors.toList());
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

        Hall hall = halls.get(hallId);
        if (hall == null) {
            throw new InvalidHallStatusException("Hall with ID " + hallId + " not found");
        }

        // Validate status transition
        String currentStatus = hall.getHallStatus();
        if (currentStatus != null && !isValidStatusTransition(currentStatus, normalizedStatus)) {
            throw new InvalidHallStatusException(
                    "Invalid status transition from " + currentStatus + " to " + normalizedStatus);
        }

        hall.setHallStatus(normalizedStatus);
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
        return halls.values().stream()
                .filter(hall -> "ACTIVE".equals(hall.getHallStatus()))
                .collect(Collectors.toList());
    }

    public List<Hall> getHallsByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String normalizedStatus = status.toUpperCase().trim();
        if (!isValidHallStatus(normalizedStatus)) {
            return new ArrayList<>();
        }

        return halls.values().stream()
                .filter(hall -> normalizedStatus.equals(hall.getHallStatus()))
                .collect(Collectors.toList());
    }

    public boolean isHallAvailable(int hallId) {
        Hall hall = halls.get(hallId);
        if (hall == null) {
            return false;
        }
        return "ACTIVE".equals(hall.getHallStatus());
    }
}

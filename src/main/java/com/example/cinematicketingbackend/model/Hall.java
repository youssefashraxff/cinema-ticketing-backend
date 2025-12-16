package com.example.cinematicketingbackend.model;

import java.util.Objects;

/**
 * Represents a cinema hall.
 */
public class Hall {
    private int hallId;
    private int capacity; // Number of seats
    private String hallType; // "STANDARD", "VIP", "IMAX", "3D", "PREMIUM"
    private String hallStatus; // "ACTIVE", "INACTIVE", "MAINTENANCE", "CLOSED"
    private int SeatPrice;
    public final int MaxNumOfShowsPerHall = 5;

    public Hall() {
        this.hallStatus = "ACTIVE"; // Default status
    }

    public Hall(int hallId, int capacity, String hallType) {
        this.hallId = hallId;
        this.capacity = capacity;
        this.hallType = hallType;
        this.hallStatus = "ACTIVE"; // Default status for new halls
    }

    // Getters and Setters
    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getHallType() {
        return hallType;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    public String getHallStatus() {
        return hallStatus;
    }

    public void setHallStatus(String hallStatus) {
        this.hallStatus = hallStatus;
    }

    public int getSeatPrice() {
        return SeatPrice;
    }

    public void setSeatPrice(int SeatPrice) {
        this.SeatPrice = SeatPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return hallId == hall.hallId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallId);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "hallId=" + hallId +
                ", capacity=" + capacity +
                ", hallType='" + hallType + '\'' +
                ", hallStatus='" + hallStatus + '\'' +
                ", SeatPrice=" + SeatPrice +
                ", MaxNumOfShowsPerHall=" + MaxNumOfShowsPerHall +
                '}';
    }
}
package com.example.cinematicketingbackend.model;

import java.util.Objects;
/**
 * Represents a movie show in a specific hall.
 */
public class Show {
    private String startTime; // Format: "yyyy-MM-dd HH:mm"
    private String finishTime; // Format: "yyyy-MM-dd HH:mm"
    private Hall hall;

    public Show() {}

    public Show(String startTime, String finishTime, Hall hall) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.hall = hall;
    }

    // Getters and Setters
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Objects.equals(startTime, show.startTime) &&
               Objects.equals(finishTime, show.finishTime) &&
               hall != null && show.hall != null &&
               hall.getHallId() == show.hall.getHallId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, finishTime, hall != null ? hall.getHallId() : 0);
    }

    @Override
    public String toString() {
        return "Show{" +
                "startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", hall=" + hall +
                '}';
    }
}
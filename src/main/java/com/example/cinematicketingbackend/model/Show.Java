package com.example.cinematicketingbackend.model;


/**
 * Represents a movie show in a specific hall.
 */
public class Show {
    private String startTime; // Format: "yyyy-MM-dd HH:mm"
    private String finishTime; // Format: "yyyy-MM-dd HH:mm"
    private Hall hall;
    // private int showId;

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
    public String toString() {
        return "Show{" +
                "startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", hall=" + hall +
                '}';
    }
}


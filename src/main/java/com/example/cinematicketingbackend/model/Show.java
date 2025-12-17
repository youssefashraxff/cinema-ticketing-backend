package com.example.cinematicketingbackend.model;


public class Show {
    private int showId;
    private String startTime; // Format: "yyyy-MM-dd HH:mm"
    private String finishTime; // Format: "yyyy-MM-dd HH:mm"
    private int hallId;

    public Show() {}

    public Show(int showId,String startTime, String finishTime) {
        this.showId = showId;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.hallId = -1;
    }

    public Show(int showId,String startTime, String finishTime , int hallId) {
        this.showId = showId;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.hallId = hallId;
    }

    // Getters and Setters
    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

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

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    @Override
    public String toString() {
        return "Show{" +
                "startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", hallId=" + hallId +
                '}';
    }
}

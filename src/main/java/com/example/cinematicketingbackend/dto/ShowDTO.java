package com.example.cinematicketingbackend.dto;

import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.model.Hall;




public class ShowDTO {

    private int showId;
    private Movie movie;
    private Hall hall;
   
    private String startTime;
    private String endTime;

    public ShowDTO() {}

    public ShowDTO(
            int showId,
            Movie movie,
            Hall hall,
            String startTime,
            String endTime
    ) {
        this.showId = showId;
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

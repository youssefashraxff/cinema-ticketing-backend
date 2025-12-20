package com.example.cinematicketingbackend.model;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Review {
    private String id;
    private int userId;
    private String movieName;
    private int rating;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public Review(String id, int userId, String movieName, int rating, String comment) {
        this.id = id;
        this.userId = userId;
        this.movieName = movieName;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    public Review(){

    }
    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMovieName(String moviename) {
        this.movieName = moviename;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMovieName() {
        return movieName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
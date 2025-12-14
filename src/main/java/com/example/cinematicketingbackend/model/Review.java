package com.example.cinematicketingbackend.model;
import java.time.LocalDateTime;

public class Review {
    private String id;
    private String userId;
    private String movieId;
    private int rating;
    private String comment;
    private LocalDateTime timestamp;

    public Review(String id, String userId, String movieId, int rating, String comment) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    public Review(){

    }
    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
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

    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
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
package com.example.cinematicketingbackend.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // This will ignore unknown fields
public class Movie {
    private String name;
    private int duration; // in minutes
    private int movieId;
    private String language;
    private double rating; // 0-10 scale
    private MovieCategoryFlyweight movieCategory; 
    private List<Show> shows; 
    
    // For JSON compatibility with old data
    private String descriptionOfMovie;

    // Default constructor for Jackson
    public Movie() {
        this.shows = new ArrayList<>();
    }

    public Movie(String name, int duration, int movieId, String language, 
                 double rating, MovieCategoryFlyweight movieCategory) {
        this.name = name;
        this.duration = duration;
        this.movieId = movieId;
        this.language = language;
        this.rating = rating;
        this.movieCategory = movieCategory;
        this.shows = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @JsonIgnore
    public int getAgeRestriction() {
        return movieCategory != null ? movieCategory.getAgeRestriction() : 0;
    }

    @JsonIgnore
    public String getType() {
        return movieCategory != null ? movieCategory.getType() : null;
    }

    @JsonIgnore
    public String getDescriptionOfMovie() {
        return movieCategory != null ? movieCategory.getDescriptionOfMovie() : null;
    }

    // This setter allows Jackson to set descriptionOfMovie but it won't be used
    @JsonProperty("descriptionOfMovie")
    public void setDescriptionOfMovie(String description) {
        this.descriptionOfMovie = description; // Just store it, won't be used
    }

    public MovieCategoryFlyweight getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(MovieCategoryFlyweight movieCategory) {
        this.movieCategory = movieCategory;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public void addShow(Show show) {
        this.shows.add(show);
    }

    public void removeShow(Show show) {
        this.shows.remove(show);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", movieId=" + movieId +
                ", language='" + language + '\'' +
                ", rating=" + rating +
                ", category=" + (movieCategory != null ? movieCategory.getType() : "null") +
                ", ageRestriction=" + getAgeRestriction() +
                ", showsCount=" + shows.size() +
                '}';
    }
}
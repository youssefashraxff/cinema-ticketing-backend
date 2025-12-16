package com.example.cinematicketingbackend.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String name;
    private int duration; // in minutes
    private int movieId;
    private String language;
    private double rating; // 0-10 scale
    private String MovieDescription;
    private String trailerURL;
    private String verticalPoster;
    private String horizontalPoster;
    private MovieCategoryFlyweight movieCategory; 
    private List<Show> shows; 
    

    public Movie() {
        this.shows = new ArrayList<>();
    }

    public Movie(String name, int duration, int movieId, String language, double rating, String movieDescription, String trailerURL, String verticalPoster, String horizontalPoster, MovieCategoryFlyweight movieCategory) {
        this.name = name;
        this.duration = duration;
        this.movieId = movieId;
        this.language = language;
        this.rating = rating;
        this.MovieDescription = movieDescription;   
        this.movieCategory = movieCategory;
        this.trailerURL = trailerURL;
        this.verticalPoster = verticalPoster;       
        this.horizontalPoster = horizontalPoster;
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

    public int getAgeRestriction() {
        return movieCategory != null ? movieCategory.getAgeRestriction() : 0;
    }

    public String getType() {
        return movieCategory != null ? movieCategory.getType() : null;
    }

    public MovieCategoryFlyweight getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(MovieCategoryFlyweight movieCategory) {
        this.movieCategory = movieCategory;
    }
     
      public String getMovieDescription() {
        return MovieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        MovieDescription = movieDescription;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getVerticalPoster() {
        return verticalPoster;
    }

    public void setVerticalPoster(String verticalPoster) {
        this.verticalPoster = verticalPoster;
    }

    public String getHorizontalPoster() {
        return horizontalPoster;
    }

    public void setHorizontalPoster(String horizontalPoster) {
        this.horizontalPoster = horizontalPoster;
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

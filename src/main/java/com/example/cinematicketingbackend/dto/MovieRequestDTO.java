package com.example.cinematicketingbackend.dto;

public class MovieRequestDTO {
  private String name;
  private int duration;
  private String language;
  private double rating;
  private String movieDescription;
  private String trailerURL;
  private String verticalPoster;
  private String horizontalPoster;
  private String categoryType;
  private int ageRestriction;

  public MovieRequestDTO() {}

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

  public String getMovieDescription() {
      return movieDescription;
  }

  public void setMovieDescription(String movieDescription) {
      this.movieDescription = movieDescription;
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

  public String getCategoryType() {
      return categoryType;
  }

  public void setCategoryType(String categoryType) {
      this.categoryType = categoryType;
  }

  public int getAgeRestriction() {
      return ageRestriction;
  }

  public void setAgeRestriction(int ageRestriction) {
      this.ageRestriction = ageRestriction;
  }
}

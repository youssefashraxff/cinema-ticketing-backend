package com.example.cinematicketingbackend.model;

public class Movie {
    private final String name;
    private final int duration; // in minutes
    private final int movieId;
    private final String language;
    private final double rating; // 0-10 scale
    private final String MovieDescription;
    private final String trailerURL;
    private final String verticalPoster;
    private final String horizontalPoster;
    private final MovieCategoryFlyweight movieCategory; 
    

    private Movie(Builder builder) {
        this.name = builder.name;
        this.duration = builder.duration;
        this.movieId = builder.movieId;
        this.language = builder.language;
        this.rating = builder.rating;
        this.MovieDescription = builder.movieDescription;
        this.trailerURL = builder.trailerURL;
        this.verticalPoster = builder.verticalPoster;
        this.horizontalPoster = builder.horizontalPoster;
        this.movieCategory = builder.movieCategory;
    }

    public static class Builder {

        private String name;
        private int duration;
        private int movieId;
        private String language;
        private double rating;
        private String movieDescription;
        private String trailerURL;
        private String verticalPoster;
        private String horizontalPoster;
        private MovieCategoryFlyweight movieCategory;

        public Builder movieId(int movieId) {
            this.movieId = movieId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder description(String description) {
            this.movieDescription = description;
            return this;
        }

        public Builder trailerURL(String trailerURL) {
            this.trailerURL = trailerURL;
            return this;
        }

        public Builder verticalPoster(String verticalPoster) {
            this.verticalPoster = verticalPoster;
            return this;
        }

        public Builder horizontalPoster(String horizontalPoster) {
            this.horizontalPoster = horizontalPoster;
            return this;
        }

        public Builder category(MovieCategoryFlyweight movieCategory) {
            this.movieCategory = movieCategory;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getLanguage() {
        return language;
    }

    public double getRating() {
        return rating;
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
     
    public String getMovieDescription() {
        return MovieDescription;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public String getVerticalPoster() {
        return verticalPoster;
    }

    public String getHorizontalPoster() {
        return horizontalPoster;
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
                '}';
    }

    
}

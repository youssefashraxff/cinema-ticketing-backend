package com.example.cinematicketingbackend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.cinematicketingbackend.exception.InvalidRatingException;
import com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException;
import com.example.cinematicketingbackend.exception.MovieNotFoundException;
import com.example.cinematicketingbackend.factory.MovieCategoryFactory;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.model.MovieCategoryFlyweight;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.util.TimeUtils;

public class MovieService {
    private Map<Integer, Movie> movies;
    private int nextMovieId;
    private ShowService showService;
    private HallService hallService; // Reference for hall-based searches
   

    public MovieService() {
        this.movies = new HashMap<>();
        this.nextMovieId = 1;
    }

    public void setShowService(ShowService showService) {
        this.showService = showService;
    }

    public void setHallService(HallService hallService) {
        this.hallService = hallService;
    }

    public Movie createMovie(String name, int duration, String language, double rating, String type, String description, int ageRestriction) {
        // Validate rating
        if (rating < 0 || rating > 10) {
            throw new InvalidRatingException("Rating must be between 0 and 10");
        }

        // Get or create flyweight category
        MovieCategoryFlyweight category = MovieCategoryFactory.getMovieCategory(type, description, ageRestriction);

        int newMovieId = nextMovieId++;
        // Create movie with flyweight reference
        Movie movie = new Movie(name, duration, newMovieId, language, rating, category);
        movies.put(newMovieId, movie);
        return movie;
    }


   
    public void deleteMovie(int movieId) {
        if (!movies.containsKey(movieId)) {
            throw new MovieNotFoundException(movieId);
        }

        Movie movie = movies.get(movieId);
        // Remove all shows associated with this movie from ShowService
        if (showService != null && movie.getShows() != null) {
            List<Show> showsToRemove = new ArrayList<>(movie.getShows());
            for (Show show : showsToRemove) {
                showService.deleteShow(movieId, show.getStartTime(), show.getHall());
            }
        }

        movies.remove(movieId);
    }

  
    public void addShow(int movieId, Show show) {
        if (show == null) {
            throw new IllegalArgumentException("Show cannot be null");
        }

        Movie movie = movies.get(movieId);
        if (movie == null) {
            throw new MovieNotFoundException(movieId);
        }

        // Validate time format
        if (!TimeUtils.validateTimeFormat(show.getStartTime()) || !TimeUtils.validateTimeFormat(show.getFinishTime())) {
            throw new com.example.cinematicketingbackend.exception.InvalidShowTimeException(
                    "Invalid time format. Expected format: yyyy-MM-dd HH:mm");
        }

        // Validate finish time is after start time
        if (!TimeUtils.isTimeAfter(show.getFinishTime(), show.getStartTime())) {
            throw new com.example.cinematicketingbackend.exception.InvalidShowTimeException(
                    "Finish time must be after start time");
        }

        // Check for hall availability through ShowService
        if (showService != null) {
            if (!showService.checkHallAvailability(show.getHall(), show.getStartTime(), show.getFinishTime())) {
                throw new com.example.cinematicketingbackend.exception.ShowOverlapException(
                        "Hall " + show.getHall().getHallId() + " is not available at the requested time");
            }
        }

        // Add show to movie
        movie.addShow(show);

        // Register show in ShowService for hall-based lookups
        if (showService != null) {
            showService.createShow(show.getStartTime(), show.getFinishTime(), show.getHall());
        }
    }

    public void updateMovieInfo(int movieId, Movie updatedMovie) {
        if (updatedMovie == null) {
            throw new IllegalArgumentException("Updated movie cannot be null");
        }

        Movie movie = movies.get(movieId);
        if (movie == null) {
            throw new MovieNotFoundException(movieId);
        }

        // Validate rating
        if (updatedMovie.getRating() < 0 || updatedMovie.getRating() > 10) {
            throw new InvalidRatingException("Rating must be between 0 and 10. Provided: " + updatedMovie.getRating());
        }

        // Update movie fields (preserve movieId and shows)
        movie.setName(updatedMovie.getName());
        movie.setDuration(updatedMovie.getDuration());
        movie.setLanguage(updatedMovie.getLanguage());
        movie.setRating(updatedMovie.getRating());
        movie.setMovieCategory(updatedMovie.getMovieCategory());
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies.values());
    }

    public List<Show> getMovieShows(int movieId) {
        Movie movie = movies.get(movieId);
        if (movie == null) {
            throw new MovieNotFoundException(movieId);
        }
        return new ArrayList<>(movie.getShows());
    }

    public Movie getMovie(int movieId) {
        Movie movie = movies.get(movieId);
        if (movie == null) {
            throw new MovieNotFoundException(movieId);
        }
        return movie;
    }

    public List<Movie> getMoviesByCategory(String type) {
        if (type == null || type.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String normalizedType = type.trim();
        return movies.values().stream()
                .filter(movie -> movie.getMovieCategory() != null && 
                                normalizedType.equalsIgnoreCase(movie.getMovieCategory().getType()))
                .collect(Collectors.toList());
    }

    public List<Movie> searchMoviesByLanguage(String language) {
    List<Movie> result = new ArrayList<>();

    if (language == null || language.trim().isEmpty()) {
        return result; // return empty list
    }

    String normalizedLanguage = language.trim();

    for (Movie movie : movies.values()) {
        if (movie.getLanguage() != null &&
            movie.getLanguage().equalsIgnoreCase(normalizedLanguage)) {
            result.add(movie);
        }
    }

    return result;
}
 
    public List<Movie> searchMoviesByShowtime(String startTime, String endTime) {
        if (startTime == null || endTime == null) {
            throw new com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException(
                    "Start time and end time cannot be null");
        }

        // Validate time format
        if (!TimeUtils.validateTimeFormat(startTime) || !TimeUtils.validateTimeFormat(endTime)) {
            throw new com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException(
                    "Invalid time format. Expected format: yyyy-MM-dd HH:mm");
        }

        // Validate startTime <= endTime
        if (TimeUtils.isTimeAfter(startTime, endTime)) {
            throw new com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException(
                    "Start time must be before or equal to end time");
        }

        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies.values()) {
            if (movie.getShows() != null && !movie.getShows().isEmpty()) {
                // Check if any show overlaps with the time range
                for (Show show : movie.getShows()) {
                    String showStart = show.getStartTime();
                    String showFinish = show.getFinishTime();
                    
                    // Check if show overlaps with search range
                    // Show overlaps if: showStart < endTime && showFinish > startTime
                    if (TimeUtils.isTimeAfter(endTime, showStart) && 
                        TimeUtils.isTimeAfter(showFinish, startTime)) {
                        result.add(movie);
                        break; // Add movie only once even if multiple shows match
                    }
                }
            }
        }
        return result;
    }

   public List<Movie> searchMoviesByDuration(int minDuration, int maxDuration) {
    // Validate input
    if (minDuration < 0 || maxDuration < 0) {
        throw new com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException(
                "Duration cannot be negative");
    }
    if (minDuration > maxDuration) {
        throw new com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException(
                "Minimum duration cannot be greater than maximum duration");
    }

    List<Movie> result = new ArrayList<>();

    // Loop through all movies and check duration
    for (Movie movie : movies.values()) {
        int duration = movie.getDuration();
        if (duration >= minDuration && duration <= maxDuration) {
            result.add(movie);
        }
    }

    return result;
}

    public List<Movie> searchMoviesByDuration(int exactDuration) {
        if (exactDuration < 0) {
            throw new InvalidSearchCriteriaException("Duration cannot be negative");
        }

        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies.values()) {
            if (movie.getDuration() == exactDuration) {
                result.add(movie);
            }
        }
        return result;
    }

    public List<Movie> searchMoviesByHall(int hallId) {
        // Validate hall exists
        if (hallService == null) {
            throw new IllegalStateException("HallService is not set");
        }

        Hall hall = hallService.getHall(hallId);
        if (hall == null) {
            throw new com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException(
                    "Hall with ID " + hallId + " does not exist");
        }

        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies.values()) {
            if (movie.getShows() != null && !movie.getShows().isEmpty()) {
                // Check if any show is in the specified hall
                for (Show show : movie.getShows()) {
                    if (show.getHall() != null && show.getHall().getHallId() == hallId) {
                        result.add(movie);
                        break; // Add movie only once even if multiple shows in same hall
                    }
                }
            }
        }
        return result;
    }

    public List<Movie> searchMoviesByRating(double minRating, double maxRating) {
    // Validate input
    if (minRating < 0.0 || maxRating > 10.0) {
        throw new com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException(
                "Rating must be between 0.0 and 10.0");
    }
    if (minRating > maxRating) {
        throw new com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException(
                "Minimum rating cannot be greater than maximum rating");
    }

    List<Movie> result = new ArrayList<>();

    // Loop through all movies and check rating
    for (Movie movie : movies.values()) {
        double rating = movie.getRating();
        if (rating >= minRating && rating <= maxRating) {
            result.add(movie);
        }
    }

    return result;
}

}

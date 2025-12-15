package com.example.cinematicketingbackend.exception;

/**
 * Exception thrown when a movie is not found in the system.
 */
public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException(int movieId) {
        super("Movie with ID " + movieId + " not found.");
    }
}


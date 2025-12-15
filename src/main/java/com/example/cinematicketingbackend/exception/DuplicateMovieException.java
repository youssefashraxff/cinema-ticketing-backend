package com.example.cinematicketingbackend.exception;

/**
 * Exception thrown when attempting to add a movie with an ID that already exists.
 */
public class DuplicateMovieException extends RuntimeException {
    public DuplicateMovieException(String message) {
        super(message);
    }

    public DuplicateMovieException(int movieId) {
        super("Movie with ID " + movieId + " already exists.");
    }
}


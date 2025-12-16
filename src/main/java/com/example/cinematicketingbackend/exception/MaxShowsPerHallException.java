package com.example.cinematicketingbackend.exception;


public class MaxShowsPerHallException extends RuntimeException {
    public MaxShowsPerHallException(String message) {
        super(message);
    }

    public MaxShowsPerHallException(int hallId, int maxShows) {
        super("Cannot create show in hall " + hallId + ". Maximum number of shows (" + maxShows + ") has been reached.");
    }
}

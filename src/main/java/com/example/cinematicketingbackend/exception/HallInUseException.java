package com.example.cinematicketingbackend.exception;

/**
 * Exception thrown when attempting to delete a hall that has scheduled shows.
 */
public class HallInUseException extends RuntimeException {
    public HallInUseException(String message) {
        super(message);
    }

    public HallInUseException(int hallId) {
        super("Hall with ID " + hallId + " cannot be deleted because it has scheduled shows.");
    }
}


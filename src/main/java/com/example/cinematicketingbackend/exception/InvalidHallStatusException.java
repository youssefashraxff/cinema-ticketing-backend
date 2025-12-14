package com.example.cinematicketingbackend.exception;

/**
 * Exception thrown when hall status operation is invalid.
 */
public class InvalidHallStatusException extends RuntimeException {
    public InvalidHallStatusException(String message) {
        super(message);
    }
}


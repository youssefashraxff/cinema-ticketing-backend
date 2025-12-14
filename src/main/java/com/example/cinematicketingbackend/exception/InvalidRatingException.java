package com.example.cinematicketingbackend.exception;

/**
 * Exception thrown when movie rating is outside the valid range (0-10).
 */
public class InvalidRatingException extends RuntimeException {
    public InvalidRatingException(String message) {
        super(message);
    }
}


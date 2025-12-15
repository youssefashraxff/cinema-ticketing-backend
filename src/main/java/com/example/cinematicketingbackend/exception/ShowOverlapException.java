package com.example.cinematicketingbackend.exception;

/**
 * Exception thrown when attempting to schedule overlapping shows in the same hall.
 */
public class ShowOverlapException extends RuntimeException {
    public ShowOverlapException(String message) {
        super(message);
    }
}


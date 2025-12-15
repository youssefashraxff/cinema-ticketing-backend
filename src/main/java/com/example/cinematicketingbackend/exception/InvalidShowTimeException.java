package com.example.cinematicketingbackend.exception;

/**
 * Exception thrown when show time validation fails.
 */
public class InvalidShowTimeException extends RuntimeException {
    public InvalidShowTimeException(String message) {
        super(message);
    }
}


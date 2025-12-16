package com.example.cinematicketingbackend.exception;

/**
 * Exception thrown when category validation fails.
 */
public class InvalidCategoryException extends RuntimeException {
    public InvalidCategoryException(String message) {
        super(message);
    }
}


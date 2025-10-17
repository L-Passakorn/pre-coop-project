package com.diaries.exception;

/**
 * Exception thrown when a user attempts to access a resource they don't have permission for.
 * Results in HTTP 403 Forbidden response.
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.demo.exception;

/**
 * Custom exception for API-related errors.
 * Thrown when API requests fail or return unexpected responses.
 */
public class ApiException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int statusCode;

    /**
     * Constructs a new ApiException with a message.
     *
     * @param message the error message
     */
    public ApiException(String message) {
        super(message);
        this.statusCode = -1;
    }

    /**
     * Constructs a new ApiException with a message and status code.
     *
     * @param message the error message
     * @param statusCode the HTTP status code
     */
    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Constructs a new ApiException with a message and cause.
     *
     * @param message the error message
     * @param cause the underlying cause
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
    }

    /**
     * Gets the HTTP status code associated with this exception.
     *
     * @return the status code, or -1 if not set
     */
    public int getStatusCode() {
        return statusCode;
    }
}

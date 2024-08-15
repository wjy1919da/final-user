package com.cmall.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // This annotation marks the exception class to trigger a specific HTTP status when thrown.
public class AccountAPIException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public AccountAPIException(HttpStatus httpStatus, String message) {
        super(message); // Passes a message to the RuntimeException constructor.
        this.httpStatus = httpStatus;
        this.message = message;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AccountAPIException(String message, HttpStatus httpStatus) {
        super(message); // Calls RuntimeException's constructor with the message.
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
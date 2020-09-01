package org.gmdev.springbootdemo.exception.review;

public class ReviewBadRequestException extends RuntimeException {

    public ReviewBadRequestException(String message) {
        super(message);
    }

    public ReviewBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

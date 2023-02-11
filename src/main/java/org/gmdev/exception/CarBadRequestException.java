package org.gmdev.exception;

public class CarBadRequestException extends RuntimeException {

    public CarBadRequestException(String message) {
        super(message);
    }

    public CarBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

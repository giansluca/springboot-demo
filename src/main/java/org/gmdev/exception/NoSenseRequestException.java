package org.gmdev.exception;

public class NoSenseRequestException extends RuntimeException {

    public NoSenseRequestException(String message) {
        super(message);
    }

    public NoSenseRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

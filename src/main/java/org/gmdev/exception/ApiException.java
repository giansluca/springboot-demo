package org.gmdev.exception;

import java.time.ZonedDateTime;

public class ApiException {

    private final ZonedDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public ApiException(ZonedDateTime timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}

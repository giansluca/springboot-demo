package org.gmdev.exception;

import java.time.LocalDateTime;

public class ApiException {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public ApiException(LocalDateTime timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
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

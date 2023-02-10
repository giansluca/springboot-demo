package org.gmdev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = {NoSenseRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(NoSenseRequestException e, WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ApiErrorResponse apiException = new ApiErrorResponse(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest.getReasonPhrase(),
                e.getMessage(),
                path
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

}

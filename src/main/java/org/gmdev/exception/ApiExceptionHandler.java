package org.gmdev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                unauthorized.value(),
                unauthorized.getReasonPhrase(),
                e.getMessage(),
                path
        );

        return new ResponseEntity<>(apiErrorResponse, unauthorized);
    }

    @ExceptionHandler(value = {NoSenseRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(NoSenseRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiErrorResponse apiException = new ApiErrorResponse(
                LocalDateTime.now(),
                badRequest.value(),
                badRequest.getReasonPhrase(),
                e.getMessage()
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

}

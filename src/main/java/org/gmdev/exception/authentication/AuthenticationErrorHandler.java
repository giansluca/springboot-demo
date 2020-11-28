package org.gmdev.exception.authentication;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Order(1)
@RestControllerAdvice
public class AuthenticationErrorHandler {

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e,  WebRequest request) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                ZonedDateTime.now(ZoneId.of("Z")),
                unauthorized.value(),
                unauthorized.getReasonPhrase(),
                e.getMessage(),
                path
        );

        return new ResponseEntity<>(apiErrorResponse, unauthorized);
    }
}

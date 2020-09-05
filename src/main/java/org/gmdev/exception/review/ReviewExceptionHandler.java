package org.gmdev.exception.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ReviewExceptionHandler {

    @ExceptionHandler(value = {ReviewBadRequestException.class})
    public ResponseEntity<Object> handleReviewBadRequestException(ReviewBadRequestException e, WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ReviewException reviewException = new ReviewException(
                ZonedDateTime.now(ZoneId.of("Z")),
                badRequest.value(),
                badRequest.getReasonPhrase(),
                e.getMessage(),
                path
        );

        return new ResponseEntity<>(reviewException, badRequest);
    }
}

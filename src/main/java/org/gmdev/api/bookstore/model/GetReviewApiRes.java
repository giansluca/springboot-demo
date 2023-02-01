package org.gmdev.api.bookstore.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetReviewApiRes {

    private final Long id;
    private final String text;
    private final Long bookId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}

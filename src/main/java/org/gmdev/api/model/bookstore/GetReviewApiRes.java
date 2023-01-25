package org.gmdev.api.model.bookstore;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetReviewApiRes {

    private Long id;
    private String text;
    private Long bookId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}

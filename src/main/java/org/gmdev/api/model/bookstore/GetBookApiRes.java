package org.gmdev.api.model.bookstore;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetBookApiRes {

    private final Long id;
    private final String title;
    //private final List<ReviewDto> reviews;
    //private final List<AuthorDto> authors;
    private final GetBookDetailApiRes bookDetail;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}

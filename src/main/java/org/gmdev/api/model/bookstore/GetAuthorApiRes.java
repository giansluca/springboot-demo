package org.gmdev.api.model.bookstore;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetAuthorApiRes {

    private Long id;
    private String name;
    private List<BookApiRes> books;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static class BookApiRes {
        // TODO
    }

}

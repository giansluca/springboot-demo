package org.gmdev.api.model.bookstore;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gmdev.model.entity.bookstore.Book;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetAuthorApiRes {

    private final Long id;
    private final String name;
    private final List<BookApiRes> books;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @Getter
    public static class BookApiRes {
        private final Long id;
        private final String title;
    }

    public static List<BookApiRes> fromEntity(List<Book> books) {
        return books.stream().
                map(book -> new BookApiRes(book.getId(), book.getTitle())).toList();
    }

}

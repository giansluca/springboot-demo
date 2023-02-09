package org.gmdev.api.bookstore.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gmdev.dao.bookstore.entity.Author;
import org.gmdev.dao.bookstore.entity.BookDetail;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetBookApiRes {

    private final Long id;
    private final String title;
    private final BookDetailApiRes bookDetail;
    private final List<AuthorApiRes> authors;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @Getter
    public static class BookDetailApiRes {
        private final Long id;
        private final Integer pages;
        private final String isbn;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @Getter
    public static class AuthorApiRes {
        private final Long id;
        private final String name;
    }

    public static BookDetailApiRes fromEntity(BookDetail bookDetail) {
        return new BookDetailApiRes(bookDetail.getId(), bookDetail.getPages(), bookDetail.getIsbn());
    }

    public static List<AuthorApiRes> fromEntity(List<Author> authors) {
        return authors.stream().
                map(author -> new AuthorApiRes(author.getId(), author.getName())).toList();
    }

}

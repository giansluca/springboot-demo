package org.gmdev.model.entity.bookstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.model.entity.bookstore.Book;

import javax.persistence.*;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "book_detail")
public class BookDetail {

    public BookDetail(Integer pages, String isbn, Book book, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.pages = pages;
        this.isbn = isbn;
        this.book = book;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "isbn")
    private String isbn;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Book book;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

}

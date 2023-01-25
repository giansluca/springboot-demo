package org.gmdev.model.entity.bookstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "book_detail")
public class BookDetail {

    public BookDetail(Integer pages, String isbn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.pages = pages;
        this.isbn = isbn;
        this.book = null;
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
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

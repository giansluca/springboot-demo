package org.gmdev.model.entity.bookstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.api.model.bookstore.GetBookApiRes;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.gmdev.api.model.bookstore.GetBookApiRes.fromEntity;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {

    public Book(String title, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.reviews = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.bookDetail = null;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private BookDetail bookDetail;

    //@ManyToMany(mappedBy = "books", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private List<Author> authors;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addBookDetail(BookDetail bookDetail) {
        this.bookDetail = bookDetail;
        bookDetail.setBook(this);
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getBooks().remove(this);
    }

    public GetBookApiRes toApiRes() {
        return new GetBookApiRes(
                id,
                title,
                fromEntity(bookDetail),
                fromEntity(authors),
                createdAt,
                updatedAt
        );
    }

    public GetBookApiRes toListApiRes() {
        return new GetBookApiRes(
                id,
                title,
                null,
                null,
                createdAt,
                updatedAt
        );
    }


}

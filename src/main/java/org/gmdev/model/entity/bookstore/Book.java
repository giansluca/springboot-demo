package org.gmdev.model.entity.bookstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {

    public Book(String title,
                Set<Review> reviews,
                Set<Author> authors,
                BookDetail bookDetail,
                ZonedDateTime createdAt,
                ZonedDateTime updatedAt) {

        this.reviews = reviews;
        this.title = title;
        this.authors = authors;
        this.bookDetail = bookDetail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews;

    //@ManyToMany(mappedBy = "books", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Set<Author> authors;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private BookDetail bookDetail;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;


}

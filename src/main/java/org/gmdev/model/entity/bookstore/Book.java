package org.gmdev.model.entity.bookstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "book")
public class Book {

    public Book(String title,
                ZonedDateTime bookTimestamp,
                BookDetail bookDetail) {

        this.title = title;
        this.bookTimestamp = bookTimestamp;
        this.bookDetail = bookDetail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "book_timestamp")
    private ZonedDateTime bookTimestamp;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<Review> reviews;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<Author> authors;

    @OneToOne(mappedBy = "book")
    private BookDetail bookDetail;


}

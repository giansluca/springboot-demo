package org.gmdev.model.entity.bookstore;

import lombok.Getter;
import lombok.Setter;
import org.gmdev.model.entity.bookstore.Book;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter @Setter
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "review_timestamp")
    private ZonedDateTime reviewTimestamp;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}

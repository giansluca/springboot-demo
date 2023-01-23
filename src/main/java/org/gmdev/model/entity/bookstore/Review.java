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
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

}

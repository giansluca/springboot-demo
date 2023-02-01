package org.gmdev.model.entity.bookstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.api.bookstore.model.GetReviewApiRes;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "review")
public class Review {

    public Review(String text, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.text = text;
        this.book = null;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public GetReviewApiRes toApiRes() {
        return new GetReviewApiRes(id, text, book.getId(), createdAt, updatedAt);
    }


}

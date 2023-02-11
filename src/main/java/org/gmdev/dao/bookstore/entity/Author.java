package org.gmdev.dao.bookstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.api.bookstore.model.GetAuthorApiRes;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.gmdev.api.bookstore.model.GetAuthorApiRes.fromEntity;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "author")
public class Author {

    public Author(String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.books = new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public GetAuthorApiRes toApiRes() {
        return new GetAuthorApiRes(
                id,
                name,
                fromEntity(books),
                createdAt,
                updatedAt
        );
    }

    public GetAuthorApiRes toApiResList() {
        return new GetAuthorApiRes(
                id,
                name,
                null,
                createdAt,
                updatedAt
        );
    }


}

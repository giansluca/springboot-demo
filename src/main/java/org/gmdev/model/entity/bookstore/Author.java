package org.gmdev.model.entity.bookstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "author")
public class Author {

    public Author(String name, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "book_author",
//            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
//    )
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Book> books;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public void addBook(Book book) {
        books.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.getAuthors().remove(this);
    }

}

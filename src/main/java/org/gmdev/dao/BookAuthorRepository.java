package org.gmdev.dao;

import org.gmdev.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends JpaRepository<Author, Long> {

    @Modifying
    @Query(
            value = "INSERT INTO book_author (book_id, author_id) VALUES (:bookId, :authorId); ",
            nativeQuery = true
    )
    void addBookAuthor(@Param("bookId") Long bookId, @Param("authorId") Long authorId);
}

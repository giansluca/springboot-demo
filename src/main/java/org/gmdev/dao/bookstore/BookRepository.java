package org.gmdev.dao.bookstore;

import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.BookGroupByReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(
            value = "SELECT * FROM book b WHERE LOWER(b.title) LIKE %:title%",
            nativeQuery = true
    )
    List<Book> searchByTitle(@Param("title") String title);

    @Query(
            value = "SELECT COUNT(*) AS review FROM book b, review r WHERE b.id = r.book_id AND b.id = :id",
            nativeQuery = true
    )
    Long countReviews(@Param("id") Long id);

    @Query(
            value = """
                    SELECT b.id, b.title, bd.isbn, COUNT(r.id) AS reviews
                    FROM book AS b
                    JOIN book_detail AS bd ON b.id = bd.id
                    LEFT JOIN review AS r ON bd.id = r.book_id
                    GROUP BY b.id, b.title, bd.isbn ORDER BY reviews DESC
                    """,
            nativeQuery = true
    )
    List<BookGroupByReview> groupByReview();

}

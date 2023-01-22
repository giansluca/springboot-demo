package org.gmdev.integration;

import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.BookGroupByReview;
import org.gmdev.setup.UtilsForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookRepositoryIntegrationTest {

    @Autowired
    UtilsForTest utils;

    @Autowired
    BookRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        utils.deleteAllBooks();
    }


    //@Test
    void itShouldSelectBooksOrderedByReviewsNumbers() {
        // Given
        utils.insertBookWithReview();
        utils.insertBookWithReview();

        // When
        List<BookGroupByReview> groupedBooks = underTest.groupByReview();

        // Then
        assertThat(groupedBooks.size()).isEqualTo(2);
    }

    //@Test
    void itShouldSelectBooksByTitleLike() {
        // Given
        utils.insertBook();

        // When
        List<Book> books = underTest.findBooksByTitleLike("test");

        // Then
        assertThat(books.size()).isEqualTo(1);
    }
}
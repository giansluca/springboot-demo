package org.gmdev.service.bookstore;

import org.gmdev.api.bookstore.model.GetReviewApiRes;
import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    BookstoreTestHelper bookstoreTestHelper;

    @Autowired
    ReviewService underTest;

    @AfterEach
    void cleanUp() {
        bookstoreTestHelper.cleanDb();
    }

    @Test
    void itShouldFindOneReview() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Review review = book.getReviews().get(0);

        // When
        GetReviewApiRes foundReview = underTest.getOne(review.getId());

        // Then
        assertThat(foundReview).isNotNull();
        assertThat(foundReview.getText()).isEqualTo("Very good book");
    }

    @Test
    void itShouldFindAllBookReviews() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);

        // When
        List<GetReviewApiRes> bookReviews = underTest.getBookReviews(book.getId());

        // Then
        assertThat(bookReviews).hasSize(2);
    }


}
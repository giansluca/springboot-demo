package org.gmdev.service.bookstore;

import org.gmdev.api.bookstore.model.CreateBookApiReq;
import org.gmdev.api.bookstore.model.CreateReviewApiReq;
import org.gmdev.api.bookstore.model.GetBookApiRes;
import org.gmdev.api.bookstore.model.UpdateBookApiReq;
import org.gmdev.dao.bookstore.entity.Author;
import org.gmdev.dao.bookstore.entity.Book;
import org.gmdev.dao.bookstore.entity.BookGroupByReview;
import org.gmdev.dao.bookstore.entity.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookstoreTestHelper bookstoreTestHelper;

    @Autowired
    BookService underTest;

    @AfterEach
    void cleanUp() {
        bookstoreTestHelper.cleanDb();
    }

    @Test
    void itShouldFindOneBook() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);

        // When
        GetBookApiRes foundBook = underTest.getOne(book.getId());

        // Then
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getTitle()).isEqualTo("The blue book");
        assertThat(foundBook.getBookDetail().getPages()).isEqualTo(100);
        assertThat(foundBook.getBookDetail().getIsbn()).isEqualTo("AAA-111-BBB");
        assertThat(foundBook.getAuthors()).hasSize(1);
        assertThat(foundBook.getAuthors().get(0).getName()).isEqualTo("Zacaria Bebop");
    }

    @Test
    void itShouldThrowIfBookNotFound() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When Then
        assertThatThrownBy(() -> underTest.getOne(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Book with id: %d not found", 99L));
    }

    @Test
    void itShouldFindAllBooks() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When
        List<GetBookApiRes> allBooks = underTest.getAll();

        // Then
        assertThat(allBooks).hasSize(books.size());
    }

    @Test
    void itShouldInsertNewBook() {
        // Given
        Author author = new Author("Zacaria Jumbo", LocalDateTime.now(), LocalDateTime.now());
        bookstoreTestHelper.saveAuthor(author);

        CreateBookApiReq bodyReq = new CreateBookApiReq("The blue book", author.getId(), 280, "AAA-111-BBB");

        // When
        Long bookId = underTest.addOne(bodyReq);
        Book savedBook = bookstoreTestHelper.findBookById(bookId);

        // Then
        assertThat(savedBook.getTitle()).isEqualTo("The blue book");
        assertThat(savedBook.getCreatedAt()).isEqualTo(savedBook.getUpdatedAt());
        assertThat(savedBook.getBookDetail().getPages()).isEqualTo(280);
        assertThat(savedBook.getBookDetail().getCreatedAt()).isNotNull();
        assertThat(savedBook.getBookDetail().getIsbn()).isEqualTo("AAA-111-BBB");
        assertThat(savedBook.getAuthors()).hasSize(1);
        assertThat(savedBook.getAuthors().get(0).getName()).isEqualTo("Zacaria Jumbo");
    }

    @Test
    void itShouldUpdateBook() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Long bookId = book.getId();
        String isbn = book.getBookDetail().getIsbn();

        UpdateBookApiReq bodyReq = new UpdateBookApiReq("updated-title", 199, null);

        // When
        underTest.updateOne(bookId, bodyReq);
        Book updatedBook = bookstoreTestHelper.findBookById(bookId);

        // Then
        assertThat(updatedBook.getTitle()).isEqualTo("updated-title");
        assertThat(updatedBook.getUpdatedAt()).isAfter(updatedBook.getCreatedAt());
        assertThat(updatedBook.getBookDetail().getPages()).isEqualTo(199);
        assertThat(updatedBook.getBookDetail().getIsbn()).isEqualTo(isbn);
    }

    @Test
    void itShouldDeleteBook() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Author author = book.getAuthors().get(0);
        int authorBooks = author.getBooks().size();
        Long bookId = book.getId();
        Long authorId = author.getId();

        // When
        underTest.deleteOne(bookId);
        Book deletedBook = bookstoreTestHelper.findBookById(bookId);
        List<Book> allBooks = bookstoreTestHelper.getAllBooks();
        Author authorBookAfterDeletion = bookstoreTestHelper.findAuthorById(authorId);

        // Then
        assertThat(allBooks).hasSize(2);
        assertThat(deletedBook).isNull();
        assertThat(authorBookAfterDeletion).isNotNull();
        assertThat(authorBookAfterDeletion.getName()).isEqualTo(author.getName());
        assertThat(authorBookAfterDeletion.getBooks()).hasSize(authorBooks - 1);
    }

    @Test
    void itShouldAddAuthorToBook() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Long bookId = book.getId();

        Author author = new Author("Zacaria Peps", LocalDateTime.now(), LocalDateTime.now());
        bookstoreTestHelper.saveAuthor(author);
        Long authorId = author.getId();

        // When
        underTest.addAuthorToBook(bookId, authorId);
        Book updateddBook = bookstoreTestHelper.findBookById(bookId);
        Author updatedAuthor = bookstoreTestHelper.findAuthorById(authorId);

        // Then
        assertThat(updateddBook.getAuthors()).hasSize(2);
        assertThat(updateddBook.getAuthors())
                .anyMatch(a -> a.getName().equals("Zacaria Peps"));
        assertThat(updatedAuthor.getBooks()).hasSize(1);
        assertThat(updatedAuthor.getBooks().get(0).getTitle()).isEqualTo("The blue book");
    }

    @Test
    void itShouldRemoveAuthorFromBook() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Long bookId = book.getId();

        Author author = book.getAuthors().get(0);
        Long authorId = author.getId();

        // When
        underTest.removeAuthorFromBook(bookId, authorId);
        Book updateddBook = bookstoreTestHelper.findBookById(bookId);
        Author updatedAuthor = bookstoreTestHelper.findAuthorById(authorId);

        // Then
        assertThat(updateddBook.getAuthors()).hasSize(0);
        assertThat(updatedAuthor.getBooks()).hasSize(1);
    }

    @Test
    void itShouldAddReviewToBook() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Long bookId = book.getId();

        CreateReviewApiReq bodyReq = new CreateReviewApiReq("very nice book, really!");

        // When
        underTest.addReviewToBook(bookId, bodyReq);
        Book updateddBook = bookstoreTestHelper.findBookById(bookId);

        // Then
        assertThat(updateddBook.getReviews()).hasSize(3);
        assertThat(updateddBook.getReviews().get(2).getText()).isEqualTo("very nice book, really!");
    }

    @Test
    void itShouldRemoveReviewFromBook() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Long bookId = book.getId();

        Review review = book.getReviews().get(0);
        Long reviewId = review.getId();

        // When
        underTest.removeReviewFromBook(bookId, reviewId);
        Book updateddBook = bookstoreTestHelper.findBookById(bookId);
        Review bookReviewAfterDeletion = bookstoreTestHelper.findReviewById(reviewId);

        // Then
        assertThat(updateddBook.getReviews()).hasSize(1);
        assertThat(bookReviewAfterDeletion).isNull();
    }

    @Test
    void itShouldSearchByTitle() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When
        List<GetBookApiRes> result = underTest.searchByTitle("fishing");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Fishing Theory");
    }

    @Test
    void itShouldGroupBookByReview() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When
        List<BookGroupByReview> result = underTest.groupByReview();

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTitle()).isEqualTo("The blue book");
        assertThat(result.get(0).getReviews()).isEqualTo(2);
        assertThat(result.get(0).getIsbn()).isEqualTo("AAA-111-BBB");

        assertThat(result.get(1).getTitle()).isEqualTo("This is the way");
        assertThat(result.get(1).getReviews()).isEqualTo(1);
        assertThat(result.get(1).getIsbn()).isEqualTo("AAA-222-BBB");

        assertThat(result.get(2).getTitle()).isEqualTo("Fishing Theory");
        assertThat(result.get(2).getReviews()).isEqualTo(0);
        assertThat(result.get(2).getIsbn()).isEqualTo("AAA-333-BBB");
    }

    @Test
    void itShouldCountBookReview() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When
        Long reviews = underTest.countReviews(books.get(0).getId());

        // Then
        assertThat(reviews).isEqualTo(2);
    }


}

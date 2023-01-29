package org.gmdev.service.bookstore;

import org.gmdev.api.model.bookstore.CreateAuthorApiReq;
import org.gmdev.api.model.bookstore.GetAuthorApiRes;
import org.gmdev.api.model.bookstore.UpdateAuthorApiReq;
import org.gmdev.model.entity.bookstore.Author;
import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.BookDetail;
import org.gmdev.model.entity.bookstore.Review;
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
class AuthorServiceTest {

    @Autowired
    BookstoreTestHelper bookstoreTestHelper;

    @Autowired
    AuthorService underTest;

    @AfterEach
    void cleanUp() {
        bookstoreTestHelper.cleanDb();
    }

    @Test
    void itShouldAddFindOneAuthor() {
        // Given
        List<Author> authors = getFakeAuthorsWithBooks();
        bookstoreTestHelper.saveAuthorList(authors);
        Author author = authors.get(0);

        // When
        GetAuthorApiRes foundAuthor = underTest.getOne(author.getId());

        // Then
        assertThat(foundAuthor.getName()).isEqualTo("Zacapa Sunto");
        assertThat(foundAuthor.getBooks()).hasSize(2);
        assertThat(foundAuthor.getBooks().get(0).getTitle()).isEqualTo("The pink book");
        assertThat(foundAuthor.getBooks().get(1).getTitle()).isEqualTo("Fishing Style");
    }

    @Test
    void itShouldThrowIfAuthorNotFound() {
        // Given
        List<Author> authors = getFakeAuthorsWithBooks();
        bookstoreTestHelper.saveAuthorList(authors);

        // When Then
        assertThatThrownBy(() -> underTest.getOne(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Author with id: %d not found", 99L));
    }

    @Test
    void itShouldAllAuthors() {
        // Given
        List<Author> authors = getFakeAuthorsWithBooks();
        bookstoreTestHelper.saveAuthorList(authors);

        // When
        List<GetAuthorApiRes> allAuthors = underTest.getAll();

        // Then
        assertThat(allAuthors).hasSize(authors.size());
    }

    @Test
    void itShouldInsertNewAuthor() {
        // Given
        CreateAuthorApiReq bodyReq = new CreateAuthorApiReq("Bob dance all");

        // When
        Long authorId = underTest.addOne(bodyReq);
        Author savedAuthor = bookstoreTestHelper.findAuthorById(authorId);

        // Then
        assertThat(savedAuthor.getName()).isEqualTo("Bob dance all");
        assertThat(savedAuthor.getCreatedAt()).isEqualTo(savedAuthor.getUpdatedAt());
        assertThat(savedAuthor.getBooks()).isEmpty();
    }

    @Test
    void itShouldUpdateAuthor() {
        // Given
        List<Author> authors = getFakeAuthorsWithBooks();
        bookstoreTestHelper.saveAuthorList(authors);
        Author author = authors.get(0);
        Long authorId = author.getId();

        UpdateAuthorApiReq bodyReq = new UpdateAuthorApiReq("Jacob Bang");

        // When
        underTest.updateOne(authorId, bodyReq);
        Author updatedAuthor = bookstoreTestHelper.findAuthorById(authorId);

        // Then
        assertThat(updatedAuthor.getName()).isEqualTo("Jacob Bang");
        assertThat(updatedAuthor.getUpdatedAt()).isAfter(updatedAuthor.getCreatedAt());
    }

    @Test
    void itShouldDeleteAuthor() {
        // Given
        List<Author> authors = getFakeAuthorsWithBooks();
        bookstoreTestHelper.saveAuthorList(authors);
        Author author = authors.get(0);
        Long authorId = author.getId();

        // When
        underTest.deleteOne(authorId);
        Author deletedAuthor = bookstoreTestHelper.findAuthorById(authorId);

        // Then
        assertThat(deletedAuthor).isNull();
    }

    private List<Author> getFakeAuthorsWithBooks() {
        LocalDateTime now = LocalDateTime.now();

        Author author1 = new Author("Zacapa Sunto", now, now);
        Author author2 = new Author("Bernard Casper", now, now);

        Review review1Book1 = new Review("good book", now, now);
        Review review2Book1 = new Review("Nice Top", now, now);
        Review review1Book2 = new Review("Boring", now, now);

        BookDetail bookDetail1 = new BookDetail(100, "AAA-111-BBB", now, now);
        Book book1 = new Book("The pink book", now, now);
        book1.addBookDetail(bookDetail1);
        book1.addAuthor(author1);
        book1.addReview(review1Book1);
        book1.addReview(review2Book1);

        BookDetail bookDetail2 = new BookDetail(180, "AAA-222-BBB", now, now);
        Book book2 = new Book("This is god", now, now);
        book2.addBookDetail(bookDetail2);
        book2.addAuthor(author2);
        book2.addReview(review1Book2);

        BookDetail bookDetail3 = new BookDetail(220, "AAA-333-BBB", now, now);
        Book book3 = new Book("Fishing Style", now, now);
        book3.addBookDetail(bookDetail3);
        book3.addAuthor(author1);

        //Author author1 = new Author("Zacapa Sunto", now, now);
//        author1.addBook(book1);
//        author1.addBook(book2);

        //Author author2 = new Author("Bernard Casper", now, now);
//        author2.addBook(book3);

        return List.of(author1, author2);
    }


}
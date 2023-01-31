package org.gmdev.service.bookstore;

import org.gmdev.api.model.bookstore.CreateAuthorApiReq;
import org.gmdev.api.model.bookstore.GetAuthorApiRes;
import org.gmdev.api.model.bookstore.UpdateAuthorApiReq;
import org.gmdev.model.entity.bookstore.Author;
import org.gmdev.model.entity.bookstore.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

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
    void itShouldAFindOneAuthor() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Author author = book.getAuthors().get(0);

        // When
        GetAuthorApiRes foundAuthor = underTest.getOne(author.getId());

        // Then
        assertThat(foundAuthor.getName()).isEqualTo("Zacaria Bebop");
        assertThat(foundAuthor.getBooks()).hasSize(2);
        assertThat(foundAuthor.getBooks().get(0).getTitle()).isEqualTo("The blue book");
        assertThat(foundAuthor.getBooks().get(1).getTitle()).isEqualTo("Fishing Theory");
    }

    @Test
    void itShouldThrowIfAuthorNotFound() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When Then
        assertThatThrownBy(() -> underTest.getOne(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Author with id: %d not found", 99L));
    }

    @Test
    void itShouldAllAuthors() {
        // Given
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When
        List<GetAuthorApiRes> allAuthors = underTest.getAll();

        // Then
        assertThat(allAuthors).hasSize(2);
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
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Author author = book.getAuthors().get(0);
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
        List<Book> books = bookstoreTestHelper.getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);
        Book book = books.get(0);
        Author author = book.getAuthors().get(0);
        Long authorId = author.getId();

        // When
        underTest.deleteOne(authorId);
        Author deletedAuthor = bookstoreTestHelper.findAuthorById(authorId);
        List<Author> allAuthors = bookstoreTestHelper.getAllAuthors();
        Book bookAuthorAfterDeleteAuthor = bookstoreTestHelper.findBookById(book.getId());

        // Then
        assertThat(allAuthors).hasSize(1);
        assertThat(deletedAuthor).isNull();
        assertThat(bookAuthorAfterDeleteAuthor).isNotNull();
        assertThat(bookAuthorAfterDeleteAuthor.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookAuthorAfterDeleteAuthor.getAuthors()).isEmpty();
    }


}
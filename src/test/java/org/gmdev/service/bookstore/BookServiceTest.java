package org.gmdev.service.bookstore;

import org.gmdev.api.model.bookstore.CreateBookApiReq;
import org.gmdev.api.model.bookstore.GetBookApiRes;
import org.gmdev.api.model.bookstore.UpdateBookApiReq;
import org.gmdev.model.entity.bookstore.Author;
import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.BookDetail;
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
        List<Book> books = getFakeBooksWithAuthors();
        Book book = books.get(0);
        bookstoreTestHelper.saveBookList(books);

        // When
        GetBookApiRes foundBook = underTest.getOne(book.getId());

        // Then
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getTitle()).isEqualTo("The blue book");
        assertThat(foundBook.getCreatedAt()).isNotNull();
        assertThat(foundBook.getBookDetail().getPages()).isEqualTo(100);
        assertThat(foundBook.getBookDetail().getIsbn()).isEqualTo("AAA-111-BBB");
        assertThat(foundBook.getAuthors()).hasSize(1);
        assertThat(foundBook.getAuthors().get(0).getName()).isEqualTo("Zacaria Bebop");
    }

    @Test
    void itShouldThrowIfBookNotFound() {
        // Given
        List<Book> books = getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When Then
        assertThatThrownBy(() -> underTest.getOne(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Book with id: %d not found", 99L));
    }

    @Test
    void itShouldFindAllBooks() {
        // Given
        List<Book> books = getFakeBooksWithAuthors();
        bookstoreTestHelper.saveBookList(books);

        // When
        List<GetBookApiRes> allBooks = underTest.getAll();

        // Then
        assertThat(allBooks).hasSize(books.size());
    }

    @Test
    void itShouldInsertANewBook() {
        // Given
        Author author = new Author("Zacaria Bebop", LocalDateTime.now(), LocalDateTime.now());
        bookstoreTestHelper.saveAuthor(author);

        CreateBookApiReq bodyReq = new CreateBookApiReq("The blue book", author.getId(), 280, "AAA-111-BBB");

        // When
        Long bookId = underTest.addOne(bodyReq);
        Book savedBook = bookstoreTestHelper.findBookById(bookId);

        // Then
        assertThat(savedBook.getTitle()).isEqualTo("The blue book");
        assertThat(savedBook.getCreatedAt()).isNotNull();
        assertThat(savedBook.getBookDetail().getPages()).isEqualTo(280);
        assertThat(savedBook.getBookDetail().getCreatedAt()).isNotNull();
        assertThat(savedBook.getBookDetail().getIsbn()).isEqualTo("AAA-111-BBB");
        assertThat(savedBook.getAuthors()).hasSize(1);
        assertThat(savedBook.getAuthors().get(0).getName()).isEqualTo("Zacaria Bebop");
    }

    @Test
    void itShouldUpdateBook() {
        // Given
        List<Book> books = getFakeBooksWithAuthors();
        Book book = books.get(0);
        bookstoreTestHelper.saveBookList(books);
        Long bookId = book.getId();
        String isbn = book.getBookDetail().getIsbn();

        UpdateBookApiReq bodyReq = new UpdateBookApiReq("updated-title", 199, isbn);

        // When
        underTest.updateOne(bookId, bodyReq);
        Book updatedBook = bookstoreTestHelper.findBookById(bookId);

        // Then
        assertThat(updatedBook.getTitle()).isEqualTo("updated-title");
        assertThat(updatedBook.getBookDetail().getPages()).isEqualTo(199);
        assertThat(updatedBook.getBookDetail().getIsbn()).isEqualTo(isbn);
    }


//
//    //@Test
//    void itShouldDeleteBook() {
//        // Given
//        Long bookId = 1L;
//
//        given(bookRepository.existsById(anyLong())).willReturn(true);
//        doNothing().when(bookRepository).deleteById(anyLong());
//
//        // When
//        // Then
//        underTest.deleteOne(bookId);
//    }
//


    private List<Book> getFakeBooksWithAuthors() {
        LocalDateTime now = LocalDateTime.now();

        Author author1 = new Author("Zacaria Bebop", now, now);
        Author author2 = new Author("Babel Tum", now, now);

        BookDetail bookDetail1 = new BookDetail(100, "AAA-111-BBB", now, now);
        Book book1 = new Book("The blue book", now, now);
        book1.addBookDetail(bookDetail1);
        book1.addAuthor(author1);

        BookDetail bookDetail2 = new BookDetail(180, "AAA-222-BBB", now, now);
        Book book2 = new Book("This is the way", now, now);
        book2.addBookDetail(bookDetail2);
        book2.addAuthor(author2);

        BookDetail bookDetail3 = new BookDetail(220, "AAA-333-BBB", now, now);
        Book book3 = new Book("Fishing Theory", now, now);
        book3.addBookDetail(bookDetail3);
        book3.addAuthor(author1);

        return List.of(book1, book2, book3);
    }


}

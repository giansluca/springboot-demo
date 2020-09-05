package org.gmdev.service;

import org.gmdev.dao.GenericDao;
import org.gmdev.model.entity.Author;
import org.gmdev.model.entity.Book;
import org.gmdev.model.entity.BookDetail;
import org.gmdev.dao.BookAuthorRepository;
import org.gmdev.dao.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookAuthorRepository bookAuthorRepository;

    @Mock
    private GenericDao<Author> authorDao;

    private BookService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new BookService(bookRepository, bookAuthorRepository, authorDao);
    }

    @Test
    void itShouldSelectAllBooks() {
        // Given an empty list
        List<Book> books = new ArrayList<>();

        given(bookRepository.findAll()).willReturn(books);

        // When
        List<Book> booksInDb = underTest.getAll();

        // Then
        assertThat(booksInDb).isEmpty();
    }

    @Test
    void itShouldSelectOneBook() {
        // Given
        // ...a book
        Long bookId = 1L;

        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        var bookDetail = new BookDetail();
        bookDetail.setIsbn("Test book detail");
        bookDetail.setPages(100);
        bookDetail.setBookDetailTimestamp(timestamp);

        var book = new Book();
        book.setId(bookId);
        book.setTitle("Test book");
        book.setBookDetail(bookDetail);
        book.setAuthors(new HashSet<>());
        book.setBookTimestamp(timestamp);

        bookDetail.setBook(book);

        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));

        // When
        Book resultBook = underTest.getOne(bookId);

        // Then
        assertThat(resultBook).isNotNull();
    }

    @Test
    void itShouldThrowIfBookNotFoundWhenSelectBook() {
        // Given
        Long bookId = 1L;
        given(bookRepository.findById(anyLong())).willReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getOne(bookId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Book with id: %d not found", bookId));
    }

    @Test
    void itShouldSetTimestampAndSaveBook() {
        // Given
        var bookDetail = new BookDetail();
        bookDetail.setIsbn("Test book detail");
        bookDetail.setPages(100);

        var author = new Author();
        author.setId(1L);
        author.setName("Fake Author");

        var book = new Book();
        book.setId(1L);
        book.setTitle("Test book");
        book.setBookDetail(bookDetail);
        book.setAuthors(new HashSet<>(Collections.singletonList(author)));

        bookDetail.setBook(book);
        author.setBooks(new HashSet<>(Collections.singletonList(book)));

        given(authorDao.findById(anyLong())).willReturn(Optional.of(author));
        given(bookRepository.save(any())).willReturn(book);

        // When
        Book savedBook = underTest.addOne(book);

        // Then
        assertThat(savedBook.getBookTimestamp()).isNotNull();
        assertThat(savedBook.getBookDetail().getBookDetailTimestamp()).isNotNull();
    }

    @Test
    void itShouldTrowIfAuthorNotFoundWhenSaveBook() {
        // Given
        // ...a book
        Long bookId = 1L;

        var bookDetail = new BookDetail();
        bookDetail.setIsbn("Test book detail");
        bookDetail.setPages(100);

        var author = new Author();
        author.setId(1L);
        author.setName("Fake Author");

        var book = new Book();
        book.setId(bookId);
        book.setTitle("Test book");
        book.setBookDetail(bookDetail);
        book.setAuthors(new HashSet<>(Collections.singletonList(author)));

        bookDetail.setBook(book);
        author.setBooks(new HashSet<>(Collections.singletonList(book)));

        given(authorDao.findById(anyLong())).willReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.addOne(book))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Author with id: %d not found", author.getId()));

        then(bookRepository).shouldHaveNoInteractions();
    }

    @Test
    void itShouldUpdateBook() {
        // Given
        // ...a book saved in Db
        Long bookId = 1L;

        var bookDetail = new BookDetail();
        bookDetail.setIsbn("Test book detail");
        bookDetail.setPages(100);
        var book = new Book();
        book.setId(bookId);
        book.setTitle("Test book");
        book.setBookDetail(bookDetail);

        // ...an updated book
        var bookDetailForUpdate = new BookDetail();
        bookDetailForUpdate.setIsbn("Test book detail - Updated");
        bookDetailForUpdate.setPages(100);
        var bookForUpdate = new Book();
        bookForUpdate.setId(bookId);
        bookForUpdate.setTitle("Test book - Updated");
        bookForUpdate.setBookDetail(bookDetailForUpdate);

        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));
        given(bookRepository.save(book)).willReturn(book);

        // When
        Book updatedBook = underTest.updateOne(bookId, bookForUpdate);

        // Then
        assertThat(updatedBook.getTitle()).contains("Updated");
        assertThat(updatedBook.getBookDetail().getIsbn()).contains("Updated");
    }

    @Test
    void itShouldThrowIfBookNotFoundWhenUpdateBook() {
        // Given
        // ...an updated book
        Long bookId = 1L;
        var bookDetailForUpdate = new BookDetail();
        bookDetailForUpdate.setIsbn("Test book detail - Updated");
        bookDetailForUpdate.setPages(100);
        var bookForUpdate = new Book();
        bookForUpdate.setId(bookId);
        bookForUpdate.setTitle("Test book - Updated");
        bookForUpdate.setBookDetail(bookDetailForUpdate);

        given(bookRepository.findById(anyLong())).willReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.updateOne(bookId, bookForUpdate))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Book with id: %d not found", bookId));

        then(bookRepository).should(never()).save(any(Book.class));
    }

    @Test
    void itShouldDeleteBook() {
        // Given
        Long bookId = 1L;

        given(bookRepository.existsById(anyLong())).willReturn(true);
        doNothing().when(bookRepository).deleteById(anyLong());

        // When
        // Then
        underTest.deleteOne(bookId);
    }

    @Test
    void itShouldThrowIfBookNotFoundWhenDeleteBook() {
        // Given
        Long bookId = 1L;

        given(bookRepository.existsById(anyLong())).willReturn(false);

        // When
        // Then
        assertThatThrownBy(() -> underTest.deleteOne(bookId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Book with id: %d not found", bookId));

        then(bookRepository).should(never()).deleteById(anyLong());
    }
}

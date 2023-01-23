package org.gmdev.service.bookstore;

import org.gmdev.model.entity.bookstore.Author;
import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.BookDetail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookstoreTestHelper bookstoreTestHelper;

    @Autowired
    private BookService underTest;

    @BeforeEach
    void setUp() {
        bookstoreTestHelper.setUp();
    }

    @AfterEach
    void cleanUp() {
        bookstoreTestHelper.cleanDb();
    }

    @Test
    void itShouldPass() {
        // Given
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        Author author = new Author("Zacaria, pop", now, now);
        BookDetail bookDetail = new BookDetail(205, "1111-2222", null, now, now);
        Book book = new Book("The blue book", Set.of(), Set.of(author), bookDetail, now, now);
        bookDetail.setBook(book);

        bookstoreTestHelper.saveBook(book);

        // When
        List<Book> allBooks = bookstoreTestHelper.findAllBooks();

        // Then
        assertThat(allBooks).hasSize(1);
        assertThat(allBooks.get(0).getBookDetail().getPages()).isEqualTo(205);
    }

//    //@Test
//    void itShouldSelectAllBooks() {
//        // Given an empty list
//        List<Book> books = new ArrayList<>();
//
//        given(bookRepository.findAll()).willReturn(books);
//
//        // When
//        List<Book> booksInDb = underTest.getAll();
//
//        // Then
//        assertThat(booksInDb).isEmpty();
//    }
//
//    //@Test
//    void itShouldSelectOneBook() {
//        // Given
//        // ...a book
//        Long bookId = 1L;
//
//        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
//        var bookDetail = new BookDetail();
//        bookDetail.setIsbn("Test book detail");
//        bookDetail.setPages(100);
//        bookDetail.setCreatedAt(timestamp);
//
//        var book = new Book();
//        book.setId(bookId);
//        book.setTitle("Test book");
//        book.setBookDetail(bookDetail);
//        book.setAuthors(new HashSet<>());
//        book.setCreatedAt(timestamp);
//
//        bookDetail.setBook(book);
//
//        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));
//
//        // When
//        Book resultBook = underTest.getOne(bookId);
//
//        // Then
//        assertThat(resultBook).isNotNull();
//    }
//
//    //@Test
//    void itShouldThrowIfBookNotFoundWhenSelectBook() {
//        // Given
//        Long bookId = 1L;
//        given(bookRepository.findById(anyLong())).willReturn(Optional.empty());
//
//        // When
//        // Then
//        assertThatThrownBy(() -> underTest.getOne(bookId))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining(String.format("Book with id: %d not found", bookId));
//    }
//
//    //@Test
//    void itShouldSetTimestampAndSaveBook() {
//        // Given
//        var bookDetail = new BookDetail();
//        bookDetail.setIsbn("Test book detail");
//        bookDetail.setPages(100);
//
//        var author = new Author();
//        author.setId(1L);
//        author.setName("Fake Author");
//
//        var book = new Book();
//        book.setId(1L);
//        book.setTitle("Test book");
//        book.setBookDetail(bookDetail);
//        book.setAuthors(new HashSet<>(Collections.singletonList(author)));
//
//        bookDetail.setBook(book);
//        author.setBooks(new HashSet<>(Collections.singletonList(book)));
//
//        given(authorDao.findById(anyLong())).willReturn(Optional.of(author));
//        given(bookRepository.save(any())).willReturn(book);
//
//        // When
//        Book savedBook = underTest.addOne(book);
//
//        // Then
//        assertThat(savedBook.getCreatedAt()).isNotNull();
//        assertThat(savedBook.getBookDetail().getCreatedAt()).isNotNull();
//    }
//
//    //@Test
//    void itShouldTrowIfAuthorNotFoundWhenSaveBook() {
//        // Given
//        // ...a book
//        Long bookId = 1L;
//
//        var bookDetail = new BookDetail();
//        bookDetail.setIsbn("Test book detail");
//        bookDetail.setPages(100);
//
//        var author = new Author();
//        author.setId(1L);
//        author.setName("Fake Author");
//
//        var book = new Book();
//        book.setId(bookId);
//        book.setTitle("Test book");
//        book.setBookDetail(bookDetail);
//        book.setAuthors(new HashSet<>(Collections.singletonList(author)));
//
//        bookDetail.setBook(book);
//        author.setBooks(new HashSet<>(Collections.singletonList(book)));
//
//        given(authorDao.findById(anyLong())).willReturn(Optional.empty());
//
//        // When
//        // Then
//        assertThatThrownBy(() -> underTest.addOne(book))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining(String.format("Author with id: %d not found", author.getId()));
//
//        then(bookRepository).shouldHaveNoInteractions();
//    }
//
//    //@Test
//    void itShouldUpdateBook() {
//        // Given
//        // ...a book saved in Db
//        Long bookId = 1L;
//
//        var bookDetail = new BookDetail();
//        bookDetail.setIsbn("Test book detail");
//        bookDetail.setPages(100);
//        var book = new Book();
//        book.setId(bookId);
//        book.setTitle("Test book");
//        book.setBookDetail(bookDetail);
//
//        // ...an updated book
//        var bookDetailForUpdate = new BookDetail();
//        bookDetailForUpdate.setIsbn("Test book detail - Updated");
//        bookDetailForUpdate.setPages(100);
//        var bookForUpdate = new Book();
//        bookForUpdate.setId(bookId);
//        bookForUpdate.setTitle("Test book - Updated");
//        bookForUpdate.setBookDetail(bookDetailForUpdate);
//
//        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));
//        given(bookRepository.save(book)).willReturn(book);
//
//        // When
//        Book updatedBook = underTest.updateOne(bookId, bookForUpdate);
//
//        // Then
//        assertThat(updatedBook.getTitle()).contains("Updated");
//        assertThat(updatedBook.getBookDetail().getIsbn()).contains("Updated");
//    }
//
//    //@Test
//    void itShouldThrowIfBookNotFoundWhenUpdateBook() {
//        // Given
//        // ...an updated book
//        Long bookId = 1L;
//        var bookDetailForUpdate = new BookDetail();
//        bookDetailForUpdate.setIsbn("Test book detail - Updated");
//        bookDetailForUpdate.setPages(100);
//        var bookForUpdate = new Book();
//        bookForUpdate.setId(bookId);
//        bookForUpdate.setTitle("Test book - Updated");
//        bookForUpdate.setBookDetail(bookDetailForUpdate);
//
//        given(bookRepository.findById(anyLong())).willReturn(Optional.empty());
//
//        // When
//        // Then
//        assertThatThrownBy(() -> underTest.updateOne(bookId, bookForUpdate))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining(String.format("Book with id: %d not found", bookId));
//
//        then(bookRepository).should(never()).save(any(Book.class));
//    }
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
//    //@Test
//    void itShouldThrowIfBookNotFoundWhenDeleteBook() {
//        // Given
//        Long bookId = 1L;
//
//        given(bookRepository.existsById(anyLong())).willReturn(false);
//
//        // When
//        // Then
//        assertThatThrownBy(() -> underTest.deleteOne(bookId))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining(String.format("Book with id: %d not found", bookId));
//
//        then(bookRepository).should(never()).deleteById(anyLong());
//    }
}

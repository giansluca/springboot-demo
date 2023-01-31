package org.gmdev.service.bookstore;

import org.gmdev.api.model.bookstore.CreateBookApiReq;
import org.gmdev.api.model.bookstore.CreateReviewApiReq;
import org.gmdev.api.model.bookstore.GetBookApiRes;
import org.gmdev.api.model.bookstore.UpdateBookApiReq;
import org.gmdev.dao.GenericDao;
import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.dao.bookstore.ReviewRepository;
import org.gmdev.model.entity.bookstore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final GenericDao<Author> authorRepository;

    @Autowired
    public BookService(BookRepository bookrepository,
                       ReviewRepository reviewRepository, GenericDao<Author> authorRepository) {

        this.bookRepository = bookrepository;
        this.reviewRepository = reviewRepository;
        this.authorRepository = authorRepository;
        this.authorRepository.setEntityClass(Author.class);
    }

    public List<GetBookApiRes> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(Book::toListApiRes)
                .toList();
    }

    public GetBookApiRes getOne(Long bookId) {
        Book book = getBookByIdOrThrow(bookId);
        return book.toApiRes();
    }

    public Long addOne(CreateBookApiReq bodyReq) {
        LocalDateTime now = LocalDateTime.now();
        Author author = getAuthorByIdOrThrow(bodyReq.getAuthorId());

        BookDetail bookDetail = new BookDetail(bodyReq.getPages(), bodyReq.getIsbn(), now, now);
        Book book = new Book(bodyReq.getBookTitle(), now, now);

        book.addAuthor(author);
        book.addBookDetail(bookDetail);

        return bookRepository.save(book).getId();
    }

    public GetBookApiRes updateOne(Long bookId, UpdateBookApiReq bodyReq) {
        Book updatedBook = bookRepository.findById(bookId)
                .map(bookInDb -> {
                    bookInDb.setUpdatedAt(LocalDateTime.now());

                    if (bodyReq.getBookTitle() != null)
                        bookInDb.setTitle(bodyReq.getBookTitle());
                    if (bodyReq.getPages() != null)
                        bookInDb.getBookDetail().setPages(bodyReq.getPages());
                    if (bodyReq.getIsbn() != null)
                        bookInDb.getBookDetail().setIsbn(bodyReq.getIsbn());

                    return bookRepository.save(bookInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("Book with id: %d not found", bookId)));

        return updatedBook.toApiRes();
    }

    public void deleteOne(Long bookId) {
        if (!bookRepository.existsById(bookId))
            throw new ResponseStatusException(NOT_FOUND,
                    String.format("Book with id: %d not found", bookId));

        bookRepository.deleteById(bookId);
    }

    public void addAuthorToBook(Long bookId, Long authorId) {
        Book book = getBookByIdOrThrow(bookId);
        Author author = getAuthorByIdOrThrow(authorId);

        if (book.getAuthors().contains(author))
            throw new ResponseStatusException(BAD_REQUEST,
                    String.format("Book with id %s and Author with id %s already associated", bookId, authorId));

        book.setUpdatedAt(LocalDateTime.now());
        book.addAuthor(author);
        bookRepository.save(book);
    }

    public void removeAuthorFromBook(Long bookId, Long authorId) {
        Book book = getBookByIdOrThrow(bookId);
        Author author = getAuthorByIdOrThrow(authorId);

        book.setUpdatedAt(LocalDateTime.now());
        book.removeAuthor(author);
        bookRepository.save(book);
    }

    public void addReviewToBook(Long bookId, CreateReviewApiReq bodyReq) {
        Book book = getBookByIdOrThrow(bookId);

        LocalDateTime now = LocalDateTime.now();
        Review review = new Review(bodyReq.getText(), now, now);

        book.setUpdatedAt(LocalDateTime.now());
        book.addReview(review);
        bookRepository.save(book);
    }

    public void removeReviewFromBook(Long bookId, Long reviewId) {
        Book book = getBookByIdOrThrow(bookId);
        Review review = getReviewByIdOrThrow(reviewId);

        book.setUpdatedAt(LocalDateTime.now());
        book.removeReview(review);
        bookRepository.save(book);
    }

    public List<GetBookApiRes> searchByTitle(String title) {
        return bookRepository.searchByTitle(title.toLowerCase())
                .stream()
                .map(Book::toListApiRes)
                .toList();
    }

    public List<BookGroupByReview> groupByReview() {
        return bookRepository.groupByReview();
    }

    public Long countReviews(Long bookId) {
        return bookRepository.countReviews(bookId);
    }

    private Author getAuthorByIdOrThrow(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("Author with id: %d not found", authorId)));
    }

    private Book getBookByIdOrThrow(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("Book with id: %d not found", bookId)));
    }

    private Review getReviewByIdOrThrow(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("Review with id: %d not found", reviewId)));
    }


}

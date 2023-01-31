package org.gmdev.service.bookstore;

import org.gmdev.dao.GenericDao;
import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.dao.bookstore.ReviewRepository;
import org.gmdev.model.entity.bookstore.Author;
import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.BookDetail;
import org.gmdev.model.entity.bookstore.Review;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class BookstoreTestHelper {

    private final BookRepository bookRepository;
    private final GenericDao<Author> authorRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public BookstoreTestHelper(BookRepository bookrepository,
                               GenericDao<Author> authorRepository,
                               ReviewRepository reviewRepository) {

        this.bookRepository = bookrepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
        this.authorRepository.setEntityClass(Author.class);
    }

    public void cleanDb() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    public void saveBookList(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long bookId) {
        Optional<Book> bookMaybe = bookRepository.findById(bookId);
        if (bookMaybe.isEmpty()) return null;

        Book book = bookMaybe.get();
        Hibernate.initialize(book.getReviews());
        Hibernate.initialize(book.getAuthors());
        return book;
    }

    public void saveAuthor(Author author) {
        authorRepository.create(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author findAuthorById(Long authorId) {
        Optional<Author> authorMaybe = authorRepository.findById(authorId);
        if (authorMaybe.isEmpty()) return null;

        Author author = authorMaybe.get();
        Hibernate.initialize(author.getBooks());
        return author;
    }

    public Review findReviewById(Long reviewId) {
        Optional<Review> reviewMaybe = reviewRepository.findById(reviewId);
        if (reviewMaybe.isEmpty()) return null;

        Review review = reviewMaybe.get();
        Hibernate.initialize(review.getBook());
        return review;
    }

    public List<Book> getFakeBooksWithAuthors() {
        LocalDateTime now = LocalDateTime.now();

        Author author1 = new Author("Zacaria Bebop", now, now);
        Author author2 = new Author("Babel Tum", now, now);

        Review review1Book1 = new Review("Very good book", now, now);
        Review review2Book1 = new Review("Nice", now, now);
        Review review1Book2 = new Review("Not good", now, now);

        BookDetail bookDetail1 = new BookDetail(100, "AAA-111-BBB", now, now);
        Book book1 = new Book("The blue book", now, now);
        book1.addBookDetail(bookDetail1);
        book1.addAuthor(author1);
        book1.addReview(review1Book1);
        book1.addReview(review2Book1);

        BookDetail bookDetail2 = new BookDetail(180, "AAA-222-BBB", now, now);
        Book book2 = new Book("This is the way", now, now);
        book2.addBookDetail(bookDetail2);
        book2.addAuthor(author2);
        book2.addReview(review1Book2);

        BookDetail bookDetail3 = new BookDetail(220, "AAA-333-BBB", now, now);
        Book book3 = new Book("Fishing Theory", now, now);
        book3.addBookDetail(bookDetail3);
        book3.addAuthor(author1);

        return List.of(book1, book2, book3);
    }


}

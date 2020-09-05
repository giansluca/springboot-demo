package org.gmdev.service;

import org.gmdev.model.entity.Author;
import org.gmdev.model.entity.Book;
import org.gmdev.dao.BookAuthorRepository;
import org.gmdev.dao.BookRepository;
import org.gmdev.dao.GenericDao;
import org.gmdev.model.entity.BookGroupByReview;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final GenericDao<Author> authorDao;

    @Autowired
    public BookService(BookRepository bookrepository,
            BookAuthorRepository bookAuthorRepository,
            GenericDao<Author> authorDao) {

        this.bookRepository = bookrepository;
        this.bookAuthorRepository = bookAuthorRepository;
        this.authorDao = authorDao;
        this.authorDao.setEntityClass(Author.class);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getOne(Long id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Book with id: %d not found", id)));

        Hibernate.initialize(book.getReviews());
        Hibernate.initialize(book.getBookDetail());
        Hibernate.initialize(book.getAuthors());

        return book;
    }

    public Book addOne(Book book) {
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        book.setBookTimestamp(timestamp);
        book.getBookDetail().setBookDetailTimestamp(timestamp);

        Set<Author> authors = book.getAuthors().stream()
                .map(author -> authorDao.findById(author.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Author with id: %d not found", author.getId())))
                )
                .collect(Collectors.toSet());

        book.getAuthors().clear();
        for (Author author : authors)
            author.addBook(book);

        return bookRepository.save(book);
    }

    public Book updateOne(Long id, Book book) {
        return bookRepository.findById(id)
                .map(bookInDb -> {
                    bookInDb.setTitle(book.getTitle());
                    bookInDb.getBookDetail().setPages(book.getBookDetail().getPages());
                    bookInDb.getBookDetail().setIsbn(book.getBookDetail().getIsbn());

                    return bookRepository.save(bookInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Book with id: %d not found", id)));
    }

    public void deleteOne(Long id) {
        if (!bookRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Book with id: %d not found", id));

        bookRepository.deleteById(id);
    }

    public List<Book> geByTitleLike(String title) {
        return bookRepository.findBooksByTitleLike(title.toLowerCase());
    }

    public List<BookGroupByReview> getBookGroupedByReviews() {
        return bookRepository.groupByReview();
    }

    public void addBookAuthor(Long bookId, Long authorId) {
        bookAuthorRepository.addBookAuthor(bookId, authorId);
    }

}

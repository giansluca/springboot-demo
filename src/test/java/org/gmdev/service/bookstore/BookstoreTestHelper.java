package org.gmdev.service.bookstore;

import org.gmdev.dao.GenericDao;
import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.model.entity.bookstore.Author;
import org.gmdev.model.entity.bookstore.Book;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class BookstoreTestHelper {

    private final BookRepository bookRepository;
    private final GenericDao<Author> authorRepository;

    @Autowired
    public BookstoreTestHelper(BookRepository bookrepository,
                               GenericDao<Author> authorRepository) {

        this.bookRepository = bookrepository;
        this.authorRepository = authorRepository;
        this.authorRepository.setEntityClass(Author.class);
    }

    public void cleanDb() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    public void saveBookList(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
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


}

package org.gmdev.service.bookstore;

import org.gmdev.dao.GenericDao;
import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.model.entity.bookstore.Author;
import org.gmdev.model.entity.bookstore.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class BookstoreTestHelper {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenericDao<Author> authorRepository;

    void setUp() {
        authorRepository.setEntityClass(Author.class);
    }

    void cleanDb() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    void saveBookList(List<Book> books) {
        bookRepository.saveAll(books);
    }

    void saveBook(Book book) {
        bookRepository.save(book);
    }

    List<Book> findAllBooks() {
        return bookRepository.findAll();
    }


}

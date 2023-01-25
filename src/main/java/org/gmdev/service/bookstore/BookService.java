package org.gmdev.service.bookstore;

import org.gmdev.api.model.bookstore.CreateBookApiReq;
import org.gmdev.api.model.bookstore.GetBookApiRes;
import org.gmdev.dao.GenericDao;
import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.model.entity.bookstore.Author;
import org.gmdev.model.entity.bookstore.Book;
import org.gmdev.model.entity.bookstore.BookDetail;
import org.gmdev.model.entity.bookstore.BookGroupByReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final GenericDao<Author> authorRepository;

    @Autowired
    public BookService(BookRepository bookrepository,
            GenericDao<Author> authorRepository) {

        this.bookRepository = bookrepository;
        this.authorRepository = authorRepository;
        this.authorRepository.setEntityClass(Author.class);
    }

    public List<GetBookApiRes> getAll() {

        // TODO
        //return bookRepository.findAll();
        return null;
    }

    public GetBookApiRes getOne(Long id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Book with id: %d not found", id)));

        return book.toApiRes();
    }

    public Long addBook(CreateBookApiReq bodyReq) {
        LocalDateTime now = LocalDateTime.now();

        Long authorId = bodyReq.getAuthorId();
        Author author = authorRepository.findById(bodyReq.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Author with id: %d not found", authorId)));

        BookDetail bookDetail = new BookDetail(bodyReq.getPages(), bodyReq.getIsbn(), now, now);
        Book book = new Book(bodyReq.getBookTitle(), now, now);

        book.addAuthor(author);
        book.addBookDetail(bookDetail);

        return bookRepository.save(book).getId();
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


}

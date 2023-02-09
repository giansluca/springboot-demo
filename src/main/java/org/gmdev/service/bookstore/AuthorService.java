package org.gmdev.service.bookstore;

import org.gmdev.api.bookstore.model.CreateAuthorApiReq;
import org.gmdev.api.bookstore.model.GetAuthorApiRes;
import org.gmdev.api.bookstore.model.UpdateAuthorApiReq;
import org.gmdev.dao.GenericDao;
import org.gmdev.dao.bookstore.BookRepository;
import org.gmdev.dao.bookstore.entity.Author;
import org.gmdev.dao.bookstore.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class AuthorService {

    private final BookRepository bookRepository;
    private final GenericDao<Author> authorRepository;

    @Autowired
    public AuthorService(BookRepository bookRepository, GenericDao<Author> authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorRepository.setEntityClass(Author.class);
    }

    public List<GetAuthorApiRes> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(Author::toApiResList)
                .toList();
    }

    public GetAuthorApiRes getOne(Long authorId) {
        Author author = getAuthorByIdOrThrow(authorId);
        return author.toApiRes();
    }

    public Long addOne(CreateAuthorApiReq bodyReq) {
        LocalDateTime now = LocalDateTime.now();
        Author author = new Author(bodyReq.getAuthorName(), now, now);

        return authorRepository.create(author).getId();
    }

    public GetAuthorApiRes updateOne(Long authorId, UpdateAuthorApiReq bodyReq) {
        Author updatedAuthor = authorRepository.findById(authorId)
                .map(authorInDb -> {
                    authorInDb.setUpdatedAt(LocalDateTime.now());

                    if (bodyReq.getAuthorName() != null)
                        authorInDb.setName(bodyReq.getAuthorName());

                    return authorRepository.update(authorInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Author with id: %d not found", authorId)));

        return updatedAuthor.toApiRes();
    }

    public void deleteOne(Long authorId) {
        Author author = getAuthorByIdOrThrow(authorId);

        detachAuthorFromBook(author);
        authorRepository.deleteById(authorId);
    }

    private void detachAuthorFromBook(Author author) {
        List<Book> authorBooks = List.copyOf(author.getBooks());

        authorBooks.forEach(book -> {
            book.removeAuthor(author);
            book.setUpdatedAt(LocalDateTime.now());
            bookRepository.save(book);
        });
    }

    private Author getAuthorByIdOrThrow(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("Author with id: %d not found", authorId)));
    }

}

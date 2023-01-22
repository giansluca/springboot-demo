package org.gmdev.service.bookstore;

import org.gmdev.dao.GenericDao;
import org.gmdev.model.entity.bookstore.Author;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class AuthorService {

    private final GenericDao<Author> authorDao;

    @Autowired
    public AuthorService(GenericDao<Author> authorDao) {
        this.authorDao = authorDao;
        this.authorDao.setEntityClass(Author.class);
    }

    public List<Author> getAll() {
        return authorDao.findAll();
    }

    public Author getOne(Long id) {
        Author author = authorDao
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Author with id: %d not found", id)));

        Hibernate.initialize(author.getBooks());
        author.getBooks().forEach(book -> Hibernate.initialize(book.getBookDetail()));

        return author;
    }

    public Author addOne(Author author) {
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        author.setAuthorTimestamp(timestamp);

        return authorDao.create(author);
    }

    public Author updateOne(Long id, Author author) {
        return authorDao.findById(id)
                .map(authorInDb -> {
                    authorInDb.setName(author.getName());

                    return authorDao.update(authorInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Author with id: %d not found", id)));
    }

    public void deleteOne(Long id) {
        if (authorDao.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Author with id: %d not found", id));

        authorDao.deleteById(id);
    }


}

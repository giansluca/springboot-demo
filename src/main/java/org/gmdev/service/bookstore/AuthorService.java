package org.gmdev.service.bookstore;

import org.gmdev.api.model.bookstore.CreateAuthorApiReq;
import org.gmdev.api.model.bookstore.GetAuthorApiRes;
import org.gmdev.api.model.bookstore.UpdateAuthorApiReq;
import org.gmdev.dao.GenericDao;
import org.gmdev.model.entity.bookstore.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AuthorService {

    private final GenericDao<Author> authorRepository;

    @Autowired
    public AuthorService(GenericDao<Author> authorRepository) {
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
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Author with id: %d not found", authorId)));

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
        if (authorRepository.findById(authorId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Author with id: %d not found", authorId));

        authorRepository.deleteById(authorId);
    }

}

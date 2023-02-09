package org.gmdev.api.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.bookstore.model.CreateBookApiReq;
import org.gmdev.api.bookstore.model.CreateReviewApiReq;
import org.gmdev.api.bookstore.model.GetBookApiRes;
import org.gmdev.api.bookstore.model.UpdateBookApiReq;
import org.gmdev.dao.bookstore.entity.BookGroupByReview;
import org.gmdev.service.bookstore.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RequestMapping("api/v1/book")
@Validated
@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<GetBookApiRes> getAll() {
        log.info("Incoming call to [BookController - getAll]");
        return bookService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{bookId}")
    public GetBookApiRes getOne(@PathVariable Long bookId) {
        log.info("Incoming call to [BookController - getOne]");
        return bookService.getOne(bookId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long addOne(@Valid @NotNull @RequestBody CreateBookApiReq bodyReq) {
        log.info("Incoming call to [BookController - addOne]");
        return bookService.addOne(bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{bookId}")
    public GetBookApiRes updateOne(
            @PathVariable Long bookId,
            @Valid @NotNull @RequestBody UpdateBookApiReq bodyReq) {

        log.info("Incoming call to [BookController - updateOne]");
        return bookService.updateOne(bookId, bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{bookId}")
    public void deleteOne(@PathVariable Long bookId) {
        log.info("Incoming call to [BookController - deleteOne]");
        bookService.deleteOne(bookId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/author/add")
    public void addAuthorToBook(
            @RequestParam(value = "bookId") @NotNull Long bookId,
            @RequestParam(value = "authorId") @NotNull Long authorId) {

        log.info("Incoming call to [BookController - addAuthorToBook]");
        bookService.addAuthorToBook(bookId, authorId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/author/remove")
    public void removeAuthorFromBook(
            @RequestParam(value = "bookId") @NotNull Long bookId,
            @RequestParam(value = "authorId") @NotNull Long authorId) {

        log.info("Incoming call to [BookController - removeAuthorFromBook]");
        bookService.removeAuthorFromBook(bookId,authorId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/review/add")
    public void addReviewToBook(
            @RequestParam(value = "bookId") @NotNull Long bookId,
            @Valid @NotNull @RequestBody CreateReviewApiReq bodyReq) {

        log.info("Incoming call to [BookController - addReviewToBook]");
        bookService.addReviewToBook(bookId, bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/review/remove")
    public void removeReviewFromBook(
            @RequestParam(value = "bookId") @NotNull Long bookId,
            @RequestParam(value = "reviewId") @NotNull Long reviewId) {

        log.info("Incoming call to [BookController - removeReviewFromBook]");
        bookService.removeReviewFromBook(bookId, reviewId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/search")
    public List<GetBookApiRes> searchByTitle(@NotNull @RequestParam("title") String title) {
        log.info("Incoming call to [BookController - searchByTitle]");
        return bookService.searchByTitle(title);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/group-by-review")
    public List<BookGroupByReview> groupByReview() {
        log.info("Incoming call to [BookController - groupByReview]");
        return bookService.groupByReview();
    }


}
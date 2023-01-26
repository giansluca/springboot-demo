package org.gmdev.api.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.model.bookstore.CreateBookApiReq;
import org.gmdev.api.model.bookstore.GetBookApiRes;
import org.gmdev.api.model.bookstore.UpdateBookApiReq;
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
    public GetBookApiRes getOne(@PathVariable("bookId") Long id) {
        log.info("Incoming call to [BookController - getOne]");
        return bookService.getOne(id);
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
            @PathVariable("bookId") Long bookId,
            @Valid @NotNull @RequestBody UpdateBookApiReq bodyReq) {

        log.info("Incoming call to [BookController - updateOne]");
        return bookService.updateOne(bookId, bodyReq);
    }

//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping(path = "{id}")
//    public void deleteOne(@PathVariable("id") Long id) {
//        bookService.deleteOne(id);
//    }
//
//    @GetMapping(path = "/search")
//    public List<GetBookApiRes> geByTitleLike(@NotNull @RequestParam("title") String title) {
//        List<Book> books = bookService.geByTitleLike(title);
//        return books.stream()
//                .map(mapper::toDtoLazy)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping(path = "/grouped")
//    public List<BookGroupByReviewDto> getBookGroupedByReviews() {
//        return bookService.getBookGroupedByReviews()
//                .stream()
//                .map(mapper::toBookGroupByReviewDto1)
//                .collect(Collectors.toList());
//    }

}
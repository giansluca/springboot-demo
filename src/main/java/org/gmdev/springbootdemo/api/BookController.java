package org.gmdev.springbootdemo.api;

import org.gmdev.springbootdemo.model.dto.BookDto;
import org.gmdev.springbootdemo.model.dto.BookGroupByReviewDto;
import org.gmdev.springbootdemo.model.entity.Book;
import org.gmdev.springbootdemo.model.mapper.entitymapper.BookMapper;
import org.gmdev.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/book")
@RestController
public class BookController {

    private final BookMapper mapper;
    private final BookService bookService;

    @Autowired
    public BookController(BookMapper mapper, BookService bookService) {
        this.mapper = mapper;
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getAll() {
        List<Book> books = bookService.getAll();
        return books.stream()
                .map(mapper::toDtoLazy)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{id}")
    public BookDto getOne(@PathVariable("id") Long id) {
        Book book = bookService.getOne(id);
        return mapper.toDto(book);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto addOne(@Valid @NotNull @RequestBody BookDto bookDto) {
        Book newBook = bookService.addOne(mapper.toEntity(bookDto));
        return mapper.toDto(newBook);
    }

    @PutMapping(path = "{id}")
    public BookDto updateOne(@PathVariable("id") Long id, @Valid @NotNull @RequestBody BookDto bookDto) {
        Book updatedBook = bookService.updateOne(id, mapper.toEntity(bookDto));
        return mapper.toDtoPlusDetail(updatedBook);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteOne(@PathVariable("id") Long id) {
        bookService.deleteOne(id);
    }

    @GetMapping(path = "/search")
    public List<BookDto> geByTitleLike(@NotNull @RequestParam("title") String title) {
        List<Book> books = bookService.geByTitleLike(title);
        return books.stream()
                .map(mapper::toDtoLazy)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/grouped")
    public List<BookGroupByReviewDto> getBookGroupedByReviews() {
        return bookService.getBookGroupedByReviews()
                .stream()
                .map(mapper::toBookGroupByReviewDto1)
                .collect(Collectors.toList());
    }

}
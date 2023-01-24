package org.gmdev.api.bookstore;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/book")
@Validated
@RestController
public class BookController {

//    private final BookService bookService;
//
//    @Autowired
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }
//
//    @GetMapping
//    public List<GetBookApiRes> getAll() {
//        List<Book> books = bookService.getAll();
//        return books.stream()
//                .map(mapper::toDtoLazy)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping(path = "{id}")
//    public GetBookApiRes getOne(@PathVariable("id") Long id) {
//        Book book = bookService.getOne(id);
//        return mapper.toDto(book);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public GetBookApiRes addOne(@Valid @NotNull @RequestBody GetBookApiRes bookDto) {
//        Book newBook = bookService.addOne(mapper.toEntity(bookDto));
//        return mapper.toDto(newBook);
//    }
//
//    @PutMapping(path = "{id}")
//    public GetBookApiRes updateOne(@PathVariable("id") Long id, @Valid @NotNull @RequestBody GetBookApiRes bookDto) {
//        Book updatedBook = bookService.updateOne(id, mapper.toEntity(bookDto));
//        return mapper.toDtoPlusDetail(updatedBook);
//    }
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
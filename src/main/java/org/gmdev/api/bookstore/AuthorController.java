package org.gmdev.api.bookstore;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/author")
@Validated
@RestController
public class AuthorController {

//    private final AuthorService authorService;
//
//    @Autowired
//    public AuthorController(
//            AuthorService authorService) {
//        this.authorService = authorService;
//    }
//
//    @GetMapping
//    public List<GetAuthorApiRes> getAll() {
//        List<Author> authors = authorService.getAll();
//        return authors.stream()
//                .map(mapper::toDtoNoBooks)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping(path = "{id}")
//    public GetAuthorApiRes getOne(@PathVariable("id") Long id) {
//        Author author = authorService.getOne(id);
//        return mapper.toDto(author);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public GetAuthorApiRes addOne(@Valid @NotNull @RequestBody GetAuthorApiRes authorDto) {
//        Author newAuthor = authorService.addOne(mapper.toEntity(authorDto));
//        return mapper.toDtoNoBooks(newAuthor);
//    }
//
//    @PutMapping(path = "{id}")
//    public GetAuthorApiRes updateOne(@PathVariable Long id, @Valid @NotNull @RequestBody GetAuthorApiRes authorDto) {
//        Author updatedAuthor = authorService.updateOne(id, mapper.toEntity(authorDto));
//        return mapper.toDtoNoBooks(updatedAuthor);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping(path = "{id}")
//    public void deleteOne(@PathVariable Long id) {
//        authorService.deleteOne(id);
//    }

}

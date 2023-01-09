package org.gmdev.api;

import org.gmdev.model.entity.Author;
import org.gmdev.model.dto.AuthorDto;
import org.gmdev.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
//    public List<AuthorDto> getAll() {
//        List<Author> authors = authorService.getAll();
//        return authors.stream()
//                .map(mapper::toDtoNoBooks)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping(path = "{id}")
//    public AuthorDto getOne(@PathVariable("id") Long id) {
//        Author author = authorService.getOne(id);
//        return mapper.toDto(author);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public AuthorDto addOne(@Valid @NotNull @RequestBody AuthorDto authorDto) {
//        Author newAuthor = authorService.addOne(mapper.toEntity(authorDto));
//        return mapper.toDtoNoBooks(newAuthor);
//    }
//
//    @PutMapping(path = "{id}")
//    public AuthorDto updateOne(@PathVariable Long id, @Valid @NotNull @RequestBody AuthorDto authorDto) {
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

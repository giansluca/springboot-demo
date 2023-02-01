package org.gmdev.api.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.bookstore.model.CreateAuthorApiReq;
import org.gmdev.api.bookstore.model.GetAuthorApiRes;
import org.gmdev.api.bookstore.model.UpdateAuthorApiReq;
import org.gmdev.service.bookstore.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RequestMapping("api/v1/author")
@Validated
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<GetAuthorApiRes> getAll() {
        log.info("Incoming call to [AuthorController - getAll]");
        return authorService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{authorId}")
    public GetAuthorApiRes getOne(@PathVariable Long authorId) {
        log.info("Incoming call to [AuthorController - getOne]");
        return authorService.getOne(authorId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long addOne(@Valid @NotNull @RequestBody CreateAuthorApiReq bodyReq) {
        log.info("Incoming call to [AuthorController - addOne]");
        return authorService.addOne(bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{authorId}")
    public GetAuthorApiRes updateOne(
            @PathVariable Long authorId,
            @Valid @NotNull @RequestBody UpdateAuthorApiReq bodyReq) {

        log.info("Incoming call to [AuthorController - addOne]");
        return authorService.updateOne(authorId, bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{authorId}")
    public void deleteOne(@PathVariable Long authorId) {
        log.info("Incoming call to [AuthorController - deleteOne]");
        authorService.deleteOne(authorId);
    }

}

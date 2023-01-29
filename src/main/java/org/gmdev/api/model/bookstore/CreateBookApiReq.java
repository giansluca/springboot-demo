package org.gmdev.api.model.bookstore;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class CreateBookApiReq {

    @NotBlank
    @Size(max = 64)
    private final String bookTitle;

    @NotNull
    private final Long authorId;

    @NotNull
    private final Integer pages;

    @NotBlank
    @Size(max = 64)
    private final String isbn;


}

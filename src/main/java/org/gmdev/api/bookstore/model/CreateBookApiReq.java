package org.gmdev.api.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

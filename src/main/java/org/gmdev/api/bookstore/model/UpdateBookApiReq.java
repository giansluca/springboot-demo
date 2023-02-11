package org.gmdev.api.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateBookApiReq {

    @Size(max = 64)
    private final String bookTitle;

    private final Integer pages;

    @Size(max = 64)
    private final String isbn;

}

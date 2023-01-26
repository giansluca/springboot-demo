package org.gmdev.api.model.bookstore;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateBookApiReq {

    @Size(max = 64)
    private String bookTitle;

    private Integer pages;

    @Size(max = 64)
    private String isbn;

}

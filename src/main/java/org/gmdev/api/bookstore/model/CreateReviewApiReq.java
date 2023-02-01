package org.gmdev.api.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class CreateReviewApiReq {

    @NotBlank
    @Size(max = 1024)
    private final String text;

}

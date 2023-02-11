package org.gmdev.api.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class CreateReviewApiReq {

    @NotBlank
    @Size(max = 1024)
    private final String text;

}

package org.gmdev.api.model.bookstore;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateAuthorApiReq {

    @NotBlank
    @Size(max = 64)
    private final String authorName;

}

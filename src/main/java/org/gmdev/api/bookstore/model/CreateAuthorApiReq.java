package org.gmdev.api.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class CreateAuthorApiReq {

    @NotBlank
    @Size(max = 64)
    private final String authorName;

}

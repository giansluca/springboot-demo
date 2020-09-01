package org.gmdev.springbootdemo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class BookAuthorDto {

    @JsonProperty("bookId")
    @NotNull
    private Long bookId;

    @JsonProperty("authorId")
    @NotNull
    private Long authorId;
}

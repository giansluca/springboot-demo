package org.gmdev.api.model.bookstore;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetBookDetailApiRes {

    private Long id;
    private Integer pages;
    private String isbn;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}

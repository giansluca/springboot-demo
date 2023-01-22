package org.gmdev.api.model.bookstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter @Setter
public class BookDetailDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("pages")
    @NotNull
    private Integer pages;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("booDetailTimestamp")
    private ZonedDateTime bookDetailTimestamp;
}

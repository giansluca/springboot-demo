package org.gmdev.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter @Setter
@JsonPropertyOrder({ "id", "title", "reviews", "authors", "bookTimestamp" })
public class BookDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    @NotBlank
    @Size(max = 64)
    private String title;

    @JsonProperty("bookTimestamp")
    private ZonedDateTime bookTimestamp;

    @JsonProperty("reviews")
    private Set<ReviewDto> reviews;

    @JsonProperty("authors")
    @NotNull
    private Set<AuthorDto> authors;

    @JsonProperty("bookDetail")
    @Valid
    @NotNull
    private BookDetailDto bookDetail;
}

package org.gmdev.api.model.bookstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Getter @Setter
@JsonPropertyOrder({"id", "text", "bookId", "reviewTimestamp"})
public class ReviewDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("text")
    @NotBlank()
    @Size(max = 512)
    private String text;

    @JsonProperty("reviewTimestamp")
    private ZonedDateTime reviewTimestamp;

    @JsonProperty("bookId")
    @NotNull()
    private Long bookId;
}

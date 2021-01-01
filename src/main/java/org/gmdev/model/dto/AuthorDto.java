package org.gmdev.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter @Setter
public class AuthorDto {

    @JsonProperty("id")
    private Long id;

    @NotBlank
    @Size(max = 64)
    @JsonProperty("name")
    private String name;

    @JsonProperty("authorTimestamp")
    private ZonedDateTime authorTimestamp;

    @JsonProperty("books")
    private Set<BookDto> books;
}

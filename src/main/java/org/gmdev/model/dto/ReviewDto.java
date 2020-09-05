package org.gmdev.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.gmdev.validator.ReviewAddGroup;
import org.gmdev.validator.ReviewUpdateGroup;

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
    @NotBlank(groups = {ReviewAddGroup.class, ReviewUpdateGroup.class})
    @Size(max = 512, groups = {ReviewAddGroup.class, ReviewUpdateGroup.class})
    private String text;

    @JsonProperty("reviewTimestamp")
    private ZonedDateTime reviewTimestamp;

    @JsonProperty("bookId")
    @NotNull(groups = ReviewAddGroup.class)
    private Long bookId;
}

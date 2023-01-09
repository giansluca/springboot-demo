package org.gmdev.api.model.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class StudentCourseApiRes {

    @JsonProperty("studentId")
    @NotNull
    private Long studentId;

    @JsonProperty("courseId")
    @NotNull
    private Long courseId;

    @JsonProperty("rating")
    private Long rating;

    @JsonProperty("insertTimestamp")
    private ZonedDateTime insertTimestamp;

    @JsonProperty("updateTimestamp")
    private ZonedDateTime updateTimestamp;
}

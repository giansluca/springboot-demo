package org.gmdev.model.dto.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter @Setter
@JsonPropertyOrder({ "id", "title", "insertTimestamp", "updateTimestamp", "studentCourse" })
public class CourseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    @NotBlank
    @Size(max = 256)
    private String title;

    @JsonProperty("insertTimestamp")
    private ZonedDateTime insertTimestamp;

    @JsonProperty("updateTimestamp")
    private ZonedDateTime updateTimestamp;

    @JsonProperty("studentCourse")
    private Set<StudentCourseDto> studentCourse;
}

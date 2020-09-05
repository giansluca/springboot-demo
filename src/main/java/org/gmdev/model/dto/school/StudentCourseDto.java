package org.gmdev.model.dto.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter @Setter
@JsonPropertyOrder({ "studentId", "student", "courseId", "course",
        "rating", "insertTimestamp", "updateTimestamp" })
public class StudentCourseDto {

    @JsonProperty("studentId")
    @NotNull
    private Long studentId;

    @JsonProperty("student")
    private StudentDto student;

    @JsonProperty("courseId")
    @NotNull
    private Long courseId;

    @JsonProperty("course")
    private CourseDto course;

    @JsonProperty("rating")
    private Long rating;

    @JsonProperty("insertTimestamp")
    private ZonedDateTime insertTimestamp;

    @JsonProperty("updateTimestamp")
    private ZonedDateTime updateTimestamp;
}

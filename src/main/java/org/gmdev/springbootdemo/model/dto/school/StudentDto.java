package org.gmdev.springbootdemo.model.dto.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter @Setter
@JsonPropertyOrder({ "id", "name", "insertTimestamp", "updateTimestamp", "studentCourse" })
public class StudentDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotBlank
    @Size(max = 64)
    private String name;

    @JsonProperty("insertTimestamp")
    private ZonedDateTime insertTimestamp;

    @JsonProperty("updateTimestamp")
    private ZonedDateTime updateTimestamp;

    @JsonProperty("studentCourse")
    private Set<StudentCourseDto> studentCourse;
}

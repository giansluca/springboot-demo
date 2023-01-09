package org.gmdev.api.model.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.*;

@AllArgsConstructor
@Getter
public class StudentApiRes {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotBlank
    @Size(max = 64)
    private String name;

    @JsonProperty("studentCourse")
    private List<StudentCourseApiRes> studentCourse;

    @JsonProperty("insertTimestamp")
    private ZonedDateTime insertTimestamp;

    @JsonProperty("updateTimestamp")
    private ZonedDateTime updateTimestamp;


}

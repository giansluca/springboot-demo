package org.gmdev.api.model.school;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class StudentApiRes {

    private Long id;
    private String name;
    private List<StudentCourseApiRes> studentCourse;
    private ZonedDateTime insertTimestamp;
    private ZonedDateTime updateTimestamp;

}

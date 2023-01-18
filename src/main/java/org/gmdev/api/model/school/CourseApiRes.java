package org.gmdev.api.model.school;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class CourseApiRes {

    private final Long courseId;
    private final String title;
    private final List<CourseStudentApiRes> courseStudents;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;

}

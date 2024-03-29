package org.gmdev.api.school.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetStudentApiRes {

    private final Long studentId;
    private final String name;
    private final List<GetStudentCourseApiRes> studentCourses;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;

}

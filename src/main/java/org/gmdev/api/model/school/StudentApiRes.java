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

    private Long studentId;
    private String name;
    private List<StudentCourseApiRes> studentCourses;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}

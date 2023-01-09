package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class CourseApiRes {

    private Long id;
    private String title;
    private List<StudentCourseApiRes> studentCourse;
    private ZonedDateTime insertTimestamp;
    private ZonedDateTime updateTimestamp;

}

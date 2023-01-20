package org.gmdev.api.model.school;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class GetStudentCourseApiRes {

    private final Long courseId;
    private final String title;
    private final Integer rating;
    private final ZonedDateTime enrolmentDate;

}

package org.gmdev.api.model.school;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class CourseStudentApiRes {

    private final Long studentId;
    private final String name;
    private final Integer rating;
    private final ZonedDateTime enrolmentDate;

}

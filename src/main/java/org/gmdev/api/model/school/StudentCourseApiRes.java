package org.gmdev.api.model.school;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class StudentCourseApiRes {

    private Long studentId;
    private Long courseId;
    private Long rating;
    private ZonedDateTime insertTimestamp;
    private ZonedDateTime updateTimestamp;

}

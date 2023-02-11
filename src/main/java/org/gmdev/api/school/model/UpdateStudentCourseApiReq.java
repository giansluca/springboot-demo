package org.gmdev.api.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UpdateStudentCourseApiReq {

    @NotNull
    private final Long studentId;
    @NotNull
    private final Long courseId;
    @NotNull
    private final Integer rating;

}

package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UpdateStudentCourseApiReq {

    @NotNull
    private final Long studentId;
    @NotNull
    private final Long courseId;

}

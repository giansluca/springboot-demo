package org.gmdev.api.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateCourseApiReq {

    @Size(max = 256)
    private final String title;

}

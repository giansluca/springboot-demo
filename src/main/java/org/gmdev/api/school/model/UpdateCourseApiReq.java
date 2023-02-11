package org.gmdev.api.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateCourseApiReq {

    @Size(max = 256)
    private final String title;

}

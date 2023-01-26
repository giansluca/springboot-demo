package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateCourseApiReq {

    @Size(max = 256)
    private final String title;

}

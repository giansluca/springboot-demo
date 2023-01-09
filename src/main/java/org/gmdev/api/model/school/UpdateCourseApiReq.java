package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter @NoArgsConstructor
public class UpdateCourseApiReq {

    @NotBlank
    @Size(max = 256)
    private String title;

}

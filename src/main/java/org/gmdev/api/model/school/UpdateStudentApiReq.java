package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateStudentApiReq {

    @NotBlank
    @Size(max = 64)
    private final String name;

}

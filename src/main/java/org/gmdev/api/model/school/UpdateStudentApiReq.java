package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateStudentApiReq {

    @Size(max = 64)
    private final String name;

}

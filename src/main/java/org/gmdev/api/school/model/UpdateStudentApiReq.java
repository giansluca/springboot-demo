package org.gmdev.api.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateStudentApiReq {

    @Size(max = 64)
    private final String name;

}

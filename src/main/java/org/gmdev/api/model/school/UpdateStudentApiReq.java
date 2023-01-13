package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UpdateStudentApiReq {

    @NotBlank
    @Size(max = 64)
    private String name;

}

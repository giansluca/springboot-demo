package org.gmdev.api.person.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class CreatePersonApiReq {

    @NotBlank
    @Size(max = 64)
    private final String name;

}

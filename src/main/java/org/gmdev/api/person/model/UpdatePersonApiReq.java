package org.gmdev.api.person.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdatePersonApiReq {

    @NotBlank
    @Size(max = 64)
    private final String name;

}

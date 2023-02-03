package org.gmdev.api.car.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateCarApiReq {

    @NotBlank
    @Size(max = 64)
    private final String name;

}

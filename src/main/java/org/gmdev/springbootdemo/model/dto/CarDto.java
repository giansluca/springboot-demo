package org.gmdev.springbootdemo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
public class CarDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    @NotBlank
    @Size(min = 6, max = 15)
    private String name;
}

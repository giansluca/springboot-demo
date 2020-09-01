package org.gmdev.springbootdemo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
public class PersonDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    @NotBlank
    @Size(min = 5, max = 50)
    private String name;
}

package org.gmdev.model.dto.quote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor
public class ValueDto {

    private Long id;
    private String quote;

}

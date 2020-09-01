package org.gmdev.springbootdemo.model.dto.quote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDto {

    private final String type;
    private final ValueDto value;

    public QuoteDto(String type, ValueDto value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public ValueDto getValue() {
        return value;
    }
}

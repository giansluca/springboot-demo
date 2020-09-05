package org.gmdev.model.dto.quote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueDto {

    private final Long id;
    private final String quote;

    public ValueDto(Long id, String quote) {
        this.id = id;
        this.quote = quote;
    }

    public Long getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }
}

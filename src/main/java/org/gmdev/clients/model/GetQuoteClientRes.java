package org.gmdev.clients.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gmdev.api.quote.model.GetQuoteApiRes;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class GetQuoteClientRes {

    private final String q;
    private final String a;
    private final String h;

    public GetQuoteApiRes toApiRes() {
        return new GetQuoteApiRes(q, a);
    }
}

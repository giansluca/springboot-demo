package org.gmdev.clients;

import org.gmdev.clients.model.GetQuoteClientRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Component
public class QuoteClient {

    private final String quoteBaseUrl;
    private final RestClient restClient;
    private final ObjectMapper mapper;

    @Autowired
    public QuoteClient(
            @Value("${application.clients.quoteBaseUrl:null}") String quoteBaseUrl,
            RestClient restClient,
            ObjectMapper mapper) {

        this.quoteBaseUrl = quoteBaseUrl;
        this.restClient = restClient;
        this.mapper = mapper;
    }

    public List<GetQuoteClientRes> getQuote() {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(quoteBaseUrl).path("/random").build().toUri();

            String response = restClient.get().uri(uri).retrieve().body(String.class);
            ObjectReader reader = mapper.readerForListOf(GetQuoteClientRes.class);
            return reader.readValue(response);
        } catch (Exception e) {
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Something failed miserably");
        }
    }


}

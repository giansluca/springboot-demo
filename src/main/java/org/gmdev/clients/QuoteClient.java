package org.gmdev.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.gmdev.api.quote.model.GetQuoteClientRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Component
public class QuoteClient {

    private final String quoteBaseUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Autowired
    public QuoteClient(
            @Value("${application.clients.quoteBaseUrl:null}") String quoteBaseUrl,
            RestTemplate restTemplate,
            ObjectMapper mapper) {

        this.quoteBaseUrl = quoteBaseUrl;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public List<GetQuoteClientRes> getQuote() {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(quoteBaseUrl)
                    .path("/random")
                    .build().toUri();

            String response = restTemplate.getForObject(uri, String.class);

            ObjectReader reader = mapper.readerForListOf(GetQuoteClientRes.class);
            return reader.readValue(response);
        } catch (Exception e) {
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Something failed miserably");
        }
    }


}

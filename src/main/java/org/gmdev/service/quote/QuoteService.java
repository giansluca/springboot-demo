package org.gmdev.service.quote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.gmdev.api.quote.model.QuoteClientRes;
import org.gmdev.configuration.QuoteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Service
public class QuoteService {

    private final QuoteConfig quoteConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Autowired
    public QuoteService(QuoteConfig quoteConfig, RestTemplate restTemplate, ObjectMapper mapper) {
        this.quoteConfig = quoteConfig;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public List<QuoteClientRes> getQuote() {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(quoteConfig.getUrl())
                    .path("/random")
                    .build()
                    .toUri();

            String response = restTemplate.getForObject(uri, String.class);

            ObjectReader reader = mapper.readerForListOf(QuoteClientRes.class);
            return reader.readValue(response);
        }
        catch (Exception e) {
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Something failed miserably");
        }
    }



}

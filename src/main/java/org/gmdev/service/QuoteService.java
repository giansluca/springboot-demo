package org.gmdev.service;

import org.gmdev.configuration.QuoteConfig;
import org.gmdev.model.dto.QuoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.*;

@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class.getName());
    private final QuoteConfig quoteConfig;

    @Autowired
    public QuoteService(QuoteConfig quoteConfig) {
        this.quoteConfig = quoteConfig;
    }

    public QuoteDto getQuote() {
        try {
            RestTemplate restTemplate = getRestTemplate();
            return restTemplate.getForObject("/random", QuoteDto.class);
        }
        catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Something failed miserably");
        }
    }

    private void logTime(Instant start) {
        logger.info("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() + "ms");
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(quoteConfig.getUrl()));
        return restTemplate;
    }

}

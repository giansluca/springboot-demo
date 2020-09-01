package org.gmdev.springbootdemo.service;

import org.gmdev.springbootdemo.configuration.QuoteConfig;
import org.gmdev.springbootdemo.model.dto.quote.QuoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@Transactional
public class QuoteService {

    private final QuoteConfig quoteConfig;
    private final RestTemplate restTemplate;

    @Autowired
    public QuoteService(QuoteConfig quoteConfig, RestTemplate restTemplate) {
        this.quoteConfig = quoteConfig;
        this.restTemplate = restTemplate;
    }

    public QuoteDto getQuote() {
        try {
            return restTemplate.getForObject(quoteConfig.getUrl(), QuoteDto.class);
        }
        catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Something failed miserably");
        }
    }

}

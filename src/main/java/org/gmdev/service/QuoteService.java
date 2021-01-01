package org.gmdev.service;

import org.gmdev.configuration.QuoteConfig;
import org.gmdev.model.dto.quote.QuoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        //testRestTemplate();
        testWebClient();

        try {
            RestTemplate restTemplate = getRestTemplate();
            return restTemplate.getForObject("/random", QuoteDto.class);
        }
        catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Something failed miserably");
        }
    }

    private void testRestTemplate() {
        RestTemplate client = getRestTemplate();

        Instant start = Instant.now();
        for (int i = 0; i < 50; i++) {
            QuoteDto quote = client.getForObject("/random", QuoteDto.class);
            System.out.println(quote.getValue().getQuote());
        }

        logTime(start);
    }

    private void testWebClient() {
        WebClient client = getWebClient();

        Instant start = Instant.now();
        Flux.range(0, 50)
                .flatMap(i -> client.get().uri("/random")
                        .retrieve()
                        .bodyToMono(QuoteDto.class))
                .doOnNext(quoteDto -> System.out.println(quoteDto.getValue().getQuote()))
                .blockLast();

        logTime(start);
    }

    private void logTime(Instant start) {
        logger.info("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() + "ms");
    }

    private WebClient getWebClient() {
        return WebClient.create(quoteConfig.getUrl());
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(quoteConfig.getUrl()));
        return restTemplate;
    }

}

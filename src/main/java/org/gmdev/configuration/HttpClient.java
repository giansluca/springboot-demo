package org.gmdev.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HttpClient {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

}

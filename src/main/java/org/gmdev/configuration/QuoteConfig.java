package org.gmdev.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class QuoteConfig {

    @Value("${application.clients.quoteBaseUrl}") private String url;

}

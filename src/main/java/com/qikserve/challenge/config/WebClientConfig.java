package com.qikserve.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${apis.wiremock-base-url}")
    private String wireMockBaseUrl;

    @Bean
    public WebClient wireMockWebClient(WebClient.Builder builder) {
        return builder.baseUrl(wireMockBaseUrl).build();
    }
}


package com.qikserve.challenge.service.impl;

import com.qikserve.challenge.exception.ExternalServiceException;
import com.qikserve.challenge.exception.ProductNotFoundException;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WiremockProductService implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WiremockProductService.class);
    private final WebClient webClient;

    public WiremockProductService(WebClient webClient) {
        this.webClient = webClient;
    }

    private Mono<? extends Product> handleException(Exception ex) {
        if (ex instanceof ProductNotFoundException || ex instanceof ExternalServiceException) {
            return Mono.error(ex);
        } else {
            LOGGER.error("An error occurred while communicating with the API to retrieve products: ", ex);
            return Mono.error(new ExternalServiceException("An error occurred while fetching data from the remote server"));
        }
    }


    @Override
    public Mono<Product> getProductById(String id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new ProductNotFoundException("Product not found")))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalServiceException("External service error")))
                .bodyToMono(Product.class)
                .onErrorResume(Exception.class,
                        this::handleException);
    }

    @Override
    public Flux<Product> getAllProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new ProductNotFoundException("Product list endpoint not found")))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalServiceException("External service error")))
                .bodyToFlux(Product.class)
                .onErrorResume(Exception.class,
                        this::handleException);
    }
}

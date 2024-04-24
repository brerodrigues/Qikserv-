package com.qikserve.challenge.service.impl;


import com.qikserve.challenge.exception.ExternalServiceException;
import com.qikserve.challenge.exception.ProductNotFoundException;
import com.qikserve.challenge.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WiremockProductServiceTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private WiremockProductService productService;

    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
    }

    @Test
    public void shouldGetProductById() {
        String productId = "123";
        Product expectedProduct = new Product(productId, "Test Product", 10, new ArrayList<>());
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.just(expectedProduct));
        when(requestHeadersUriSpec.uri(eq("/products/{id}"), eq("123"))).thenReturn(requestHeadersSpec);

        Mono<Product> result = productService.getProductById(productId);

        StepVerifier.create(result)
                .expectNext(expectedProduct)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldThrowsNotFoundWhenProductByIdNotFound() {
        String productId = "123";
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.error(new ProductNotFoundException("Product not found")));
        when(requestHeadersUriSpec.uri(eq("/products/{id}"), eq("123"))).thenReturn(requestHeadersSpec);

        Mono<Product> result = productService.getProductById(productId);

        StepVerifier.create(result)
                .expectError(ProductNotFoundException.class)
                .verify();
    }

    @Test
    public void shouldGetAllProducts() {
        Product product1 = new Product("1", "Product 1", 10, new ArrayList<>());
        Product product2 = new Product("2", "Product 2", 10, new ArrayList<>());
        List<Product> productList = List.of(product1, product2);
        when(responseSpec.bodyToFlux(Product.class)).thenReturn(Flux.fromIterable(productList));
        when(requestHeadersUriSpec.uri(eq("/products"))).thenReturn(requestHeadersSpec);

        Flux<Product> result = productService.getAllProducts();

        StepVerifier.create(result)
                .expectNext(product1, product2)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldThrowsProductNotFoundExceptionWhenNotFoundProducts() {
        when(responseSpec.bodyToFlux(Product.class)).thenReturn(Flux.error(new ProductNotFoundException("Product list endpoint not found")));
        when(requestHeadersUriSpec.uri(eq("/products"))).thenReturn(requestHeadersSpec);

        Flux<Product> result = productService.getAllProducts();

        StepVerifier.create(result)
                .expectError(ProductNotFoundException.class)
                .verify();
    }

    @Test
    public void shouldThrowsExternalServiceExceptionWhenExternalServiceReturnsError() {
        when(responseSpec.bodyToFlux(Product.class)).thenReturn(Flux.error(new ExternalServiceException("External service error")));
        when(requestHeadersUriSpec.uri(eq("/products"))).thenReturn(requestHeadersSpec);

        Flux<Product> result = productService.getAllProducts();

        StepVerifier.create(result)
                .expectError(ExternalServiceException.class)
                .verify();
    }
}


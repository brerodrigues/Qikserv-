package com.qikserve.challenge.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void shouldGetProductById() {
        String productId = "123";
        Product expectedProduct  = new Product(productId, "Test Product", 10, new ArrayList<>());

        when(productService.getProductById(productId)).thenReturn(Mono.just(expectedProduct ));

        webClient.get()
                .uri("/products/{id}", productId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .value(product -> assertEquals(expectedProduct.getId(), product.getId()))
                .value(product -> assertEquals(expectedProduct.getName(), product.getName()))
                .value(product -> assertEquals(expectedProduct.getPrice(), product.getPrice()));;
    }

    @Test
    public void shouldGetAllProducts() {
        Product product1 = new Product("1", "Product 1", 10, new ArrayList<>());
        Product product2 = new Product("2", "Product 2", 10, new ArrayList<>());
        List<Product> productList = List.of(product1, product2);

        when(productService.getAllProducts()).thenReturn(Flux.fromIterable(productList));

        webClient.get()
                .uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(2)
                .consumeWith(response -> {
                    List<Product> responseBody = response.getResponseBody();
                    assert responseBody != null;
                    assertEquals(productList.get(0).getId(), responseBody.get(0).getId());
                    assertEquals(productList.get(0).getName(), responseBody.get(0).getName());
                    assertEquals(productList.get(0).getPrice(), responseBody.get(0).getPrice());
                    assertEquals(productList.get(1).getId(), responseBody.get(1).getId());
                    assertEquals(productList.get(1).getName(), responseBody.get(1).getName());
                    assertEquals(productList.get(1).getPrice(), responseBody.get(1).getPrice());
                });
    }
}

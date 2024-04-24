package com.qikserve.challenge.controller;

import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.service.ProductService;
import com.qikserve.challenge.service.impl.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebFluxTest(controllers = CartController.class)
class CartControllerTest {

    @MockBean
    CartService cartService;

    @MockBean
    ProductService productService;

    CartController cartController;

    @BeforeEach
    void setUp() {
        cartController = new CartController(cartService, productService);
    }

    @Test
    void shouldGetProductCartItens() {
        List<Product> expectedItems = Arrays.asList(new Product("1", "Test Product", 10, new ArrayList<>()),
                new Product("2", "Test Product2", 10, new ArrayList<>()));
        when(cartService.getAllItems()).thenReturn(expectedItems);

        List<Product> actualItems = cartController.getCartItems();

        assertEquals(expectedItems, actualItems);
    }

    @Test
    void shouldReturnOkWhenAddProductToCart() {
        String productId = "1";
        Product product = new Product(productId, "Test Product", 10, new ArrayList<>());
        when(productService.getProductById(productId)).thenReturn(Mono.just(product));

        ResponseEntity<Void> response = cartController.addItemToCart(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartService, times(1)).addItem(product);
    }

    @Test
    void shouldReturnTotalPriceForProductsInCart() {
        int expectedTotalPrice = 50;
        when(cartService.calculateTotalPrice()).thenReturn(expectedTotalPrice);

        ResponseEntity<Integer> response = cartController.calculateTotal();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTotalPrice, response.getBody());
    }

    @Test
    void shouldReturnTotalSavings() {
        int expectedSavings = 20;
        when(cartService.calculateSavings()).thenReturn(expectedSavings);

        ResponseEntity<Integer> response = cartController.calculateSavings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSavings, response.getBody());
    }
}

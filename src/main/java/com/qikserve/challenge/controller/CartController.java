package com.qikserve.challenge.controller;

import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.service.ProductService;
import com.qikserve.challenge.service.impl.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/items")
    public List<Product> getCartItems() {
        return cartService.getAllItems();
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Void> addItemToCart(@PathVariable String productId) {
        productService.getProductById(productId)
                .flatMap(product -> {
                    cartService.addItem(product);
                    return Mono.empty();
                })
                .block();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> calculateTotal() {
        return ResponseEntity.ok(cartService.calculateTotalPrice());
    }

    @GetMapping("/savings")
    public ResponseEntity<Integer> calculateSavings() {
        return ResponseEntity.ok(cartService.calculateSavings());
    }
}

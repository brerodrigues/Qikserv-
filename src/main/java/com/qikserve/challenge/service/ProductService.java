package com.qikserve.challenge.service;

import com.qikserve.challenge.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> getProductById(String id);
    Flux<Product> getAllProducts();
}

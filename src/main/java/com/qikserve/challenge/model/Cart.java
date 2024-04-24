package com.qikserve.challenge.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Product> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public Cart(List<Product> items) {
        this.items = items;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public void addItem(Product product) {
        items.add(product);
    }

    public long countProductInCart(Product product) {
        return items.stream()
                .filter(p -> p.getId().equals(product.getId()))
                .count();
    }

    public long countProductWithoutPromotionInCart(Product product) {
        return items.stream()
                .filter(p -> p.getId().equals(product.getId()) && !p.isPromotional())
                .count();
    }
}


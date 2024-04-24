package com.qikserve.challenge.service.impl;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.strategies.promotions.PromotionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final PromotionService promotionService;
    private final Cart cart;

    public CartService(PromotionService promotionService) {
        this.promotionService = promotionService;
        this.cart = new Cart();
    }

    public void addItem(Product product) {
        product.getPromotions().forEach(promotion -> {
            PromotionStrategy promotionStrategy = promotionService.getPromotionStrategy(promotion.getType());
            if (promotionStrategy != null) {
                promotionStrategy.applyPromotion(product, promotion, cart);
            } else {
                LOGGER.error("No promotion strategy found for type: {} in product: {}", promotion.getType(), product);
            }
        });

        cart.addItem(product);
    }

    public List<Product> getAllItems() {
        return cart.getItems();
    }

    public int calculateSavings() {
        int total = calculateTotalPrice();
        int totalWithPromotions = calculateTotalPriceWithPromotions();

        return total - totalWithPromotions;
    }

    public int calculateTotalPrice() {
        int total = 0;

        for (Product product : cart.getItems()) {
            total += product.getPrice();
        }

        return total;
    }

    private int calculateTotalPriceWithPromotions() {
        int total = 0;

        for (Product product : cart.getItems()) {
            if (product.isPromotional()) {
                total += product.getPromocionalPrice();
            } else {
                total += product.getPrice();
            }
        }

        return total;
    }
}


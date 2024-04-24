package com.qikserve.challenge.strategies.promotions;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.model.Promotion;

public interface PromotionStrategy {
    void applyPromotion(Product product, Promotion promotion, Cart cart);
    String getType();
}


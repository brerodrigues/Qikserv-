package com.qikserve.challenge.strategies.promotions;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.model.Promotion;
import org.springframework.stereotype.Service;

@Service
public class FlatPercentOverridePromotion implements PromotionStrategy {

    @Override
    public String getType() {
        return "FLAT_PERCENT";
    }

    @Override
    public void applyPromotion(Product product, Promotion promotion, Cart cart) {
        double discount = promotion.getAmount() / 100.0;
        double discountedPrice = product.getPrice() - (product.getPrice() * discount);

        if (discountedPrice < 0) {
            discountedPrice = 0;
        }

        product.setPromocionalPrice((int) Math.round(discountedPrice));
        product.setIsPromotional(true);
    }
}

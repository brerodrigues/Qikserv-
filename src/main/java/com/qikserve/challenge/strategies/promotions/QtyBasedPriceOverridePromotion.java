package com.qikserve.challenge.strategies.promotions;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.model.Promotion;
import org.springframework.stereotype.Service;

@Service
public class QtyBasedPriceOverridePromotion implements PromotionStrategy {

    @Override
    public String getType() {
        return "QTY_BASED_PRICE_OVERRIDE";
    }

    @Override
    public void applyPromotion(Product product, Promotion promotion, Cart cart) {
        long productCountInCart = cart.countProductWithoutPromotionInCart(product) + 1;

        if (productCountInCart >= promotion.getRequiredQty()) {
            product.setPromocionalPrice(promotion.getPrice());
            product.setIsPromotional(true);
        }
    }
}

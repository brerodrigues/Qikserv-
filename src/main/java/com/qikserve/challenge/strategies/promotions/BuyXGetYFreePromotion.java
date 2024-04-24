package com.qikserve.challenge.strategies.promotions;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.model.Promotion;
import org.springframework.stereotype.Service;

@Service
public class BuyXGetYFreePromotion implements PromotionStrategy {

    @Override
    public String getType() {
        return "BUY_X_GET_Y_FREE";
    }

    @Override
    public void applyPromotion(Product product, Promotion promotion, Cart cart) {
        long totalProductsWithoutPromotion = cart.countProductWithoutPromotionInCart(product) + 1;

        if (isPromotionApplicable(totalProductsWithoutPromotion, promotion.getRequiredQty())) {
            Product freeProduct = createPromotionalProduct(product);
            cart.addItem(freeProduct);
        }
    }

    private boolean isPromotionApplicable(long totalProductsWithoutPromotion, int requiredQty) {
        return totalProductsWithoutPromotion % requiredQty == 0;
    }

    private Product createPromotionalProduct(Product product) {
        Product freeProduct = new Product(product);
        freeProduct.setPromocionalPrice(0);
        freeProduct.setIsPromotional(true);
        return freeProduct;
    }
}

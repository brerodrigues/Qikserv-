package com.qikserve.challenge.strategies.promotions;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.model.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class FlatPercentOverridePromotionTest {

    private FlatPercentOverridePromotion promotionStrategy;

    @BeforeEach
    public void setUp() {
        promotionStrategy = new FlatPercentOverridePromotion();
    }

    @Test
    public void shouldApplyFlatPercentDiscount() {
        Product product = new Product("1", "Product", 200, new ArrayList<>());
        Promotion promotion = new Promotion("PROMO1", "FLAT_PERCENT", 10, 0, 20);
        Cart cart = mock(Cart.class);

        promotionStrategy.applyPromotion(product, promotion, cart);

        assertTrue(product.isPromotional());
        assertEquals(160, product.getPromocionalPrice());
    }

    @Test
    public void shouldNotSetNegativePriceWhemAmoutIsBiggerThanMaxPercent() {
        Product product = new Product("1", "Product", 50, new ArrayList<>());
        Promotion promotion = new Promotion("PROMO1", "FLAT_PERCENT", 10, 0, 110);
        Cart cart = mock(Cart.class);

        promotionStrategy.applyPromotion(product, promotion, cart);

        assertTrue(product.isPromotional());
        assertEquals(0, product.getPromocionalPrice());
    }
}

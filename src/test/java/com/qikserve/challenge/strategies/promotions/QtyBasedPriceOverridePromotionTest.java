package com.qikserve.challenge.strategies.promotions;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.model.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class QtyBasedPriceOverridePromotionTest {

    private QtyBasedPriceOverridePromotion promotionStrategy;

    @BeforeEach
    public void setUp() {
        promotionStrategy = new QtyBasedPriceOverridePromotion();
    }

    @Test
    public void shouldApplyPromotionWhenRequiredQtyIsMet() {
        Product product = new Product("1", "Product", 100, new ArrayList<>());
        Promotion promotion = new Promotion("PROMO1", "QTY_BASED_PRICE_OVERRIDE", 3, 3, 50);
        Cart cart = mock(Cart.class);

        when(cart.countProductWithoutPromotionInCart(product)).thenReturn(2L);

        promotionStrategy.applyPromotion(product, promotion, cart);

        assertTrue(product.isPromotional());
        assertEquals(3, product.getPromocionalPrice());
    }

    @Test
    public void shouldNotApplyPromotionWhenRequiredQtyIsNotMet() {
        Product product = new Product("1", "Product", 100, new ArrayList<>());
        Promotion promotion = new Promotion("PROMO1", "QTY_BASED_PRICE_OVERRIDE", 3, 3, 50);
        Cart cart = mock(Cart.class);

        when(cart.countProductWithoutPromotionInCart(product)).thenReturn(1L);

        promotionStrategy.applyPromotion(product, promotion, cart);

        assertFalse(product.isPromotional());
        assertEquals(100, product.getPrice());
    }
}

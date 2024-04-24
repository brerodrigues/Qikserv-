package com.qikserve.challenge.strategies.promotions;


import static org.mockito.Mockito.*;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.model.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BuyXGetYFreePromotionTest {

    private BuyXGetYFreePromotion promotionStrategy;

    @BeforeEach
    public void setUp() {
        promotionStrategy = new BuyXGetYFreePromotion();
    }

    @Test
    public void shouldAddFreeProductWhenRequiredQuantityIsInCart() {
        Product product = new Product("1", "Product", 100, new ArrayList<>());
        Promotion promotion = new Promotion("PROMO2", "BUY_X_GET_Y_FREE", 3, 0, 0);
        Cart cart = mock(Cart.class);
        when(cart.countProductWithoutPromotionInCart(product)).thenReturn(2L);

        promotionStrategy.applyPromotion(product, promotion, cart);

        verify(cart).addItem(argThat(p -> p.getId().equals(product.getId()) && p.getPromocionalPrice() == 0));
    }

    @Test
    public void shouldNotAddFreeProductWhenRequiredQuantityIsNotInCart() {
        Product product = new Product("1", "Product", 100, new ArrayList<>());
        Promotion promotion = new Promotion("PROMO2", "BUY_X_GET_Y_FREE", 3, 0, 0);
        Cart cart = mock(Cart.class);
        when(cart.countProductWithoutPromotionInCart(product)).thenReturn(1L);

        promotionStrategy.applyPromotion(product, promotion, cart);

        verify(cart, never()).addItem(any(Product.class));
    }
}

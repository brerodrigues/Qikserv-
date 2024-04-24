package com.qikserve.challenge.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.qikserve.challenge.model.Cart;
import com.qikserve.challenge.model.Product;
import com.qikserve.challenge.model.Promotion;
import com.qikserve.challenge.strategies.promotions.PromotionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

public class CartServiceTest {

    @Mock
    private PromotionService promotionService;

    @InjectMocks
    private CartService cartService;

    @Mock
    private Cart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldApplyPromotionWhenProductHasPromotionAndStrategy() {
        Product product = new Product("1", "Test Product", 100, new ArrayList<>());
        Promotion promotion = new Promotion("PROMO1", "Discount", 10, 10, 10.0);
        product.getPromotions().add(promotion);
        PromotionStrategy mockStrategy = mock(PromotionStrategy.class);

        when(promotionService.getPromotionStrategy(promotion.getType())).thenReturn(mockStrategy);

        cartService.addItem(product);

        verify(mockStrategy, times(1)).applyPromotion(any(Product.class), any(Promotion.class), any(Cart.class));
    }

    @Test
    public void shouldNotApplyPromotionWhenPromotionStrategyNotFound() {
        Product product = new Product("1", "Test Product", 100, new ArrayList<>());
        Promotion promotion = new Promotion("PROMO1", "Discount", 10, 10, 10.0);
        product.getPromotions().add(promotion);
        PromotionStrategy mockStrategy = mock(PromotionStrategy.class);

        when(promotionService.getPromotionStrategy(promotion.getType())).thenReturn(null);

        cartService.addItem(product);

        verify(mockStrategy, times(0)).applyPromotion(any(Product.class), any(Promotion.class), any(Cart.class));
    }

    @Test
    public void shouldReturnTotalPriceOfProductsIncart() {
        Product product1 = new Product("1", "Test Product", 100, new ArrayList<>());
        Product product2 = new Product("1", "Test Product", 100, new ArrayList<>());
        cartService.addItem(product1);
        cartService.addItem(product2);

        int total = cartService.calculateTotalPrice();

        assertEquals(product1.getPrice() + product2.getPrice(), total);
    }

    @Test
    public void shouldReturnSavings() {
        Product regularProduct = new Product("1", "Regular Product", 100, new ArrayList<>());
        Product promoProduct = new Product("2", "Promo Product", 100, new ArrayList<>());
        promoProduct.setPromocionalPrice(80);
        promoProduct.setIsPromotional(true);

        cartService.addItem(regularProduct);
        cartService.addItem(promoProduct);

        int savings = cartService.calculateSavings();

        assertEquals(20, savings);
    }

    @Test
    public void shouldReturnAllItensOfCart() {
        Product product = new Product("1", "Test Product", 100, new ArrayList<>());
        cartService.addItem(product);

        List<Product> items = cartService.getAllItems();

        assertEquals(1, items.size());
        assertEquals(product, items.getFirst());
    }
}

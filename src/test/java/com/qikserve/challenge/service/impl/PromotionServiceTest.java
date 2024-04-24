package com.qikserve.challenge.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.qikserve.challenge.strategies.promotions.BuyXGetYFreePromotion;
import com.qikserve.challenge.strategies.promotions.FlatPercentOverridePromotion;
import com.qikserve.challenge.strategies.promotions.PromotionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class PromotionServiceTest {

    private PromotionService promotionService;
    private final String buyXGetYFreePromotionType = "BUY_X_GET_Y_FREE";
    private final String flatPercentOverridePromotionType = "FLAT_PERCENT";

    @Mock
    private BuyXGetYFreePromotion buyXGetYFreePromotion;

    @Mock
    private FlatPercentOverridePromotion flatPercentOverridePromotion;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        List<PromotionStrategy> strategies = new ArrayList<>();
        when(buyXGetYFreePromotion.getType()).thenReturn(buyXGetYFreePromotionType);
        when(flatPercentOverridePromotion.getType()).thenReturn(flatPercentOverridePromotionType);
        strategies.add(buyXGetYFreePromotion);
        strategies.add(flatPercentOverridePromotion);

        promotionService = new PromotionService(strategies);
    }

    @Test
    public void shouldGetBuyXGetYFreePromotionStrategy() {
        PromotionStrategy strategy = promotionService.getPromotionStrategy(buyXGetYFreePromotionType);
        assertEquals(buyXGetYFreePromotion, strategy);
    }

    @Test
    public void shouldGetFlatPercentOverridePromotionStrategy() {
        PromotionStrategy strategy = promotionService.getPromotionStrategy(flatPercentOverridePromotionType);
        assertEquals(flatPercentOverridePromotion, strategy);
    }

    @Test
    public void shouldReturnNullWhenDidntFindPromotionStrategy() {
        String type = "INVALID_TYPE";
        assertNull(promotionService.getPromotionStrategy(type));
    }
}

package com.qikserve.challenge.service.impl;

import com.qikserve.challenge.strategies.promotions.PromotionStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    private final Map<String, PromotionStrategy> promotionStrategies;

    public PromotionService(List<PromotionStrategy> strategies) {
        promotionStrategies = strategies.stream()
                .collect(Collectors.toMap(PromotionStrategy::getType, Function.identity()));
    }

    public PromotionStrategy getPromotionStrategy(String type) {
        return promotionStrategies.get(type);
    }
}


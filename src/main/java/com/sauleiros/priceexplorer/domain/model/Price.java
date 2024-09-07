package com.sauleiros.priceexplorer.domain.model;

import java.time.LocalDateTime;
import java.util.Currency;

public record Price(
        Long brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long priceList,
        Long productId,
        Long priority,
        Double price,
        Currency currency,
        LocalDateTime lastUpdate,
        String lastUpdateBy
) {
}

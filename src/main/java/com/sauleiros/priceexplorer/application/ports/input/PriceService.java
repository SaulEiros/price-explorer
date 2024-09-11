package com.sauleiros.priceexplorer.application.ports.input;

import com.sauleiros.priceexplorer.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceService {
    Price findPrice(LocalDateTime date, Long brandId, Long productId);
}

package com.sauleiros.priceexplorer.application.ports.output;

import com.sauleiros.priceexplorer.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> findPrices(LocalDateTime date, Long brandId, Long productId);
}

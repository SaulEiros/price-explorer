package com.sauleiros.priceexplorer.application.ports.input.filter;

import java.time.LocalDateTime;
import java.util.Optional;

public record PriceFilter(
        Optional<LocalDateTime> date,
        Optional<Long> productId,
        Optional<Long> brandId
) {
}

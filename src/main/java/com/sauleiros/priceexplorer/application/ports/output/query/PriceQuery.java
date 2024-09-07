package com.sauleiros.priceexplorer.application.ports.output.query;

import java.time.LocalDateTime;
import java.util.Optional;

public record PriceQuery(
        Optional<LocalDateTime> date,
        Optional<Long> productId,
        Optional<Long> brandId)
{}

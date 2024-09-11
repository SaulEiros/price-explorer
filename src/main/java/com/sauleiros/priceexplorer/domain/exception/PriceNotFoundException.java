package com.sauleiros.priceexplorer.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(LocalDateTime date, Long brandId, Long productId) {
        super(createMessage(date, brandId, productId));
    }

    private static String createMessage(LocalDateTime date, Long brandId, Long productId) {
        return String.format("Price not found for product %d, brand %d on date %s",
                productId, brandId, date.toString());
    }
}

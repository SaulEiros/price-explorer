package com.sauleiros.priceexplorer.infrastructure.adapters.input.rest.models;

import com.sauleiros.priceexplorer.domain.model.Price;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Model for details of a Price")
public record RestPrice(
        @Schema(
                description = "The Id of the Product",
                example = "1",
                type = "long"
        )
        Long productId,
        @Schema(
                description = "The Id of the Brand",
                example = "1",
                type = "long"
        )
        Long brandId,
        @Schema(
                description = "The Id of the Price List",
                example = "1",
                type = "long"
        )
        Long priceList,
        @Schema(
                description = "The price of the Product",
                example = "1.00",
                type = "double"
        )
        Double price,
        @Schema(
                description = "The ISO representation of the Curreny",
                example = "EUR",
                type = "string"
        )
        String currency,
        @Schema(
                description = "The date and time of the start of the Price",
                example = "2020-01-01T00:00:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime startDate,
        @Schema(
                description = "The date and time of the end of the Price",
                example = "2020-12-31T23:59:59",
                type = "string",
                format = "date-time"
        )
        LocalDateTime endDate
) {
    public static RestPrice fromDomain(Price price) {
        return new RestPrice(
                price.productId(),
                price.brandId(),
                price.priceList(),
                price.price(),
                price.currency().getCurrencyCode(),
                price.startDate(),
                price.endDate()
        );
    }
}

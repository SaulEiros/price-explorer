package com.sauleiros.priceexplorer.infrastructure.adapters.input.rest.models;

public record ErrorMessageModel(
        Integer status,
        String message
) {
}

package com.sauleiros.priceexplorer.application.ports.output;

import com.sauleiros.priceexplorer.application.ports.output.query.PriceQuery;
import com.sauleiros.priceexplorer.domain.model.Price;

import java.util.List;

public interface PriceRepository {
    List<Price> findPrices(PriceQuery query);
}

package com.sauleiros.priceexplorer.application.ports.input;

import com.sauleiros.priceexplorer.application.ports.input.filter.PriceFilter;
import com.sauleiros.priceexplorer.domain.model.Price;

import java.util.List;

public interface PriceService {
    List<Price> findPrices(PriceFilter filter);
}

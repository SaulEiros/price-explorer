package com.sauleiros.priceexplorer.application.service;

import com.sauleiros.priceexplorer.application.ports.input.PriceService;
import com.sauleiros.priceexplorer.application.ports.input.filter.PriceFilter;
import com.sauleiros.priceexplorer.domain.model.Price;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    @Override
    public List<Price> findPrices(PriceFilter filter) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

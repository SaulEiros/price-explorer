package com.sauleiros.priceexplorer.infrastructure.adapters.output.sql;

import com.sauleiros.priceexplorer.application.ports.output.PriceRepository;
import com.sauleiros.priceexplorer.application.ports.output.query.PriceQuery;
import com.sauleiros.priceexplorer.domain.model.Price;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SqlPriceRepository implements PriceRepository {
    @Override
    public List<Price> findPrices(PriceQuery query) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

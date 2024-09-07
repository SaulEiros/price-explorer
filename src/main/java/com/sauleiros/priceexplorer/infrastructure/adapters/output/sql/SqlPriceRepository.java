package com.sauleiros.priceexplorer.infrastructure.adapters.output.sql;

import com.sauleiros.priceexplorer.application.ports.output.PriceRepository;
import com.sauleiros.priceexplorer.application.ports.output.query.PriceQuery;
import com.sauleiros.priceexplorer.domain.model.Price;
import com.sauleiros.priceexplorer.infrastructure.adapters.output.sql.model.SqlPrice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SqlPriceRepository implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;

    public SqlPriceRepository(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }

    @Override
    public List<Price> findPrices(PriceQuery query) {
        return this.jpaPriceRepository.findPrices(
                query.date().orElse(null),
                query.productId().orElse(null),
                query.brandId().orElse(null)
        ).stream().map(SqlPrice::toDomain).toList();
    }
}

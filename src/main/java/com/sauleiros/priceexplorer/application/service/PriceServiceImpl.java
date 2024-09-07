package com.sauleiros.priceexplorer.application.service;

import com.sauleiros.priceexplorer.application.ports.input.PriceService;
import com.sauleiros.priceexplorer.application.ports.input.filter.PriceFilter;
import com.sauleiros.priceexplorer.application.ports.output.PriceRepository;
import com.sauleiros.priceexplorer.application.ports.output.query.PriceQuery;
import com.sauleiros.priceexplorer.domain.model.Price;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public List<Price> findPrices(PriceFilter filter) {
        PriceQuery query = buildQuery(filter);
        List<Price> prices = priceRepository.findPrices(query);

        return filter.date().isPresent() ? filterPricesByPriority(prices) : prices;
    }

    private PriceQuery buildQuery(PriceFilter filter) {
        return new PriceQuery(
             filter.date(),
             filter.productId(),
             filter.brandId()
        );
    }

    private List<Price> filterPricesByPriority(List<Price> prices) {
        return prices.stream()
                .collect(Collectors.groupingBy(
                        price -> new ProductBrandKey(price.productId(), price.brandId()),
                        Collectors.maxBy(Comparator.comparingLong(Price::priority))
                ))
                .values()
                .stream()
                .flatMap(Optional::stream)
                .toList();
    }

    private record ProductBrandKey(
            Long productId,
            Long brandId
    ){}
}



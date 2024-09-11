package com.sauleiros.priceexplorer.application.service;

import com.sauleiros.priceexplorer.application.ports.input.PriceService;
import com.sauleiros.priceexplorer.application.ports.output.PriceRepository;
import com.sauleiros.priceexplorer.domain.exception.PriceNotFoundException;
import com.sauleiros.priceexplorer.domain.model.Price;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price findPrice(LocalDateTime date, Long brandId, Long productId) {
        List<Price> prices = priceRepository.findPrices(date, brandId, productId);

        return filterPricesByPriority(prices, date, brandId, productId);
    }

    private Price filterPricesByPriority(List<Price> prices, LocalDateTime date, Long brandId, Long productId) {
        return prices.stream()
                .max(Comparator.comparingLong(Price::priority))
                .orElseThrow(() -> new PriceNotFoundException(date, brandId, productId));
    }
}



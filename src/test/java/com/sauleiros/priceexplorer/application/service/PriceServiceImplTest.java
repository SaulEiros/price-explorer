package com.sauleiros.priceexplorer.application.service;

import com.sauleiros.priceexplorer.application.ports.output.PriceRepository;
import com.sauleiros.priceexplorer.domain.exception.PriceNotFoundException;
import com.sauleiros.priceexplorer.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;


    @Test
    void givenOneExistingPrice_whenSearchingForMatchingProperties_thenPriceIsReturned() {
        // GIVEN
        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        Long brandId = random.nextLong();
        Long productId = random.nextLong();
        Long priority = random.nextLong();
        Double price = random.nextDouble();

        LocalDateTime searchedDate = LocalDateTime.of(2024, 7, 2, 12, 0);

        Price expectedPrice = generatePrice(startDate, endDate, brandId, productId, priority, price);

        when(priceRepository.findPrices(searchedDate, brandId, productId)).thenReturn(List.of(expectedPrice));

        // WHEN
        Price result = priceService.findPrice(searchedDate, brandId, productId);

        // THEN
        verify(priceRepository, times(1)).findPrices(searchedDate, brandId, productId);
        assertNotNull(result);
        assertEquals(expectedPrice, result);
    }

    @Test
    void givenTwoExistingPricesForSameBrandAndProduct_whenSearchingForMatchingProperties_thenHigherPriorityPriceIsReturned() {
        // GIVEN
        Random random = new Random();

        Long brandId = random.nextLong();
        Long productId = random.nextLong();

        Long lowPriority = random.nextLong(Long.MAX_VALUE - 10L);
        LocalDateTime lowPriorityStartDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime lowPriorityEndDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        Double lowPriorityPriceCost = random.nextDouble();

        Price lowPriorityPrice = generatePrice(lowPriorityStartDate, lowPriorityEndDate, brandId, productId, lowPriority, lowPriorityPriceCost);

        Long highPriority = lowPriority + random.nextLong(1, 10);
        LocalDateTime highPriorityStartDate = LocalDateTime.of(2024, 4, 1, 0, 0);
        LocalDateTime highPriorityEndDate = LocalDateTime.of(2024, 10, 31, 23, 59);
        Double highPriorityPriceCost = random.nextDouble();

        Price highPriorityPrice = generatePrice(highPriorityStartDate, highPriorityEndDate, brandId, productId, highPriority, highPriorityPriceCost);

        List<Price> prices = List.of(lowPriorityPrice, highPriorityPrice);

        LocalDateTime searchedDate = LocalDateTime.of(2024, 7, 2, 12, 0);

        when(priceRepository.findPrices(searchedDate, brandId, productId)).thenReturn(prices);

        // WHEN
        Price result = priceService.findPrice(searchedDate, brandId, productId);

        // THEN
        verify(priceRepository, times(1)).findPrices(searchedDate, brandId, productId);
        assertNotNull(result);
        assertEquals(highPriorityPrice, result);
    }

    @Test
    void givenAnyPrice_whenSearchingForNoMatchingProperties_thenPriceNotFoundIsThrown() {
        // GIVEN
        Random random = new Random();

        LocalDateTime searchedDate = LocalDateTime.of(2024, 7, 2, 12, 0);
        Long searchedProductId = random.nextLong();
        Long searchedBrandId = random.nextLong();

        when(priceRepository.findPrices(searchedDate, searchedBrandId, searchedProductId)).thenReturn(List.of());

        // WHEN & THEN
        assertThrows(
                PriceNotFoundException.class,
                () -> priceService.findPrice(searchedDate, searchedBrandId, searchedProductId)
        );

        verify(priceRepository, times(1)).findPrices(searchedDate, searchedBrandId, searchedProductId);

    }

    private Price generatePrice(LocalDateTime startDate, LocalDateTime endDate, Long brandId, Long productId, Long priority, Double price) {
        return new Price(
                brandId,
                startDate,
                endDate,
                1L,
                productId,
                priority,
                price,
                Currency.getInstance("EUR"),
                LocalDateTime.now(),
                "testUser"
        );
    }
}

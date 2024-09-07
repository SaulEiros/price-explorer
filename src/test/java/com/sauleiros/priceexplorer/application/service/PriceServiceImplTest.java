package com.sauleiros.priceexplorer.application.service;

import com.sauleiros.priceexplorer.application.ports.input.filter.PriceFilter;
import com.sauleiros.priceexplorer.application.ports.output.PriceRepository;
import com.sauleiros.priceexplorer.application.ports.output.query.PriceQuery;
import com.sauleiros.priceexplorer.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    private List<Price> prices;

    @BeforeEach()
    void setUp() {
        Price price1 = new Price(
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                1L,
                35455L,
                0L,
                35.50,
                Currency.getInstance("EUR"),
                LocalDateTime.of(2020, 3, 26, 14, 49, 7),
                "testUser"
        );

        Price price2 = new Price(
                1L,
                LocalDateTime.of(2020, 7, 14, 0, 0),
                LocalDateTime.of(2020, 11, 30, 23, 59),
                1L,
                35455L,
                1L,
                40.00,
                Currency.getInstance("EUR"),
                LocalDateTime.of(2020, 3, 26, 14, 49, 7),
                "testUser"
        );

        prices = List.of(price1, price2);
    }

    @Test
    void givenNoFilters_whenGetAllPrices_thenReturnAllPrices() {
        // GIVEN
        PriceFilter filter = new PriceFilter(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        when(priceRepository.findPrices(any())).thenReturn(prices);

        // WHEN
        List<Price> result = priceService.findPrices(filter);

        // THEN
        ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
        verify(priceRepository, Mockito.times(1)).findPrices(any());
        verify(priceRepository).findPrices(captor.capture());

        PriceQuery capturedQuery = captor.getValue();

        assertEquals(Optional.empty(), capturedQuery.date());
        assertEquals(Optional.empty(), capturedQuery.productId());
        assertEquals(Optional.empty(), capturedQuery.brandId());

        assertNotNull(result);
        assertEquals(prices, result);
    }

    @Test
    void givenExistingDateFilter_whenGetAllPrices_thenPriorityPricesAreReturned() {
        // GIVEN
        Optional<LocalDateTime> expectedDate = Optional
                .of(LocalDateTime.of(2020, 9, 20, 16, 24));

        PriceFilter filter = new PriceFilter(
                expectedDate,
                Optional.empty(),
                Optional.empty()
        );

        List<Price> expectedResult = prices.stream().filter(price -> price.priority().equals(1L)).toList();

        when(priceRepository.findPrices(any())).thenReturn(prices);

        // WHEN
        List<Price> result = priceService.findPrices(filter);

        // THEN
        ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
        verify(priceRepository, Mockito.times(1)).findPrices(any());
        verify(priceRepository).findPrices(captor.capture());

        PriceQuery capturedQuery = captor.getValue();

        assertEquals(expectedDate, capturedQuery.date());
        assertEquals(Optional.empty(), capturedQuery.productId());
        assertEquals(Optional.empty(), capturedQuery.brandId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResult, result);
    }

    @Test
    void givenNonExistingDateFilter_whenGetAllPrices_thenNoPricesAreReturned() {
        // GIVEN
        Optional<LocalDateTime> expectedDate = Optional
                .of(LocalDateTime.of(2019, 9, 20, 16, 24));

        PriceFilter filter = new PriceFilter(
                expectedDate,
                Optional.empty(),
                Optional.empty()
        );

        when(priceRepository.findPrices(any())).thenReturn(List.of());

        // WHEN
        List<Price> result = priceService.findPrices(filter);

        // THEN
        ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
        verify(priceRepository, Mockito.times(1)).findPrices(any());
        verify(priceRepository).findPrices(captor.capture());

        PriceQuery capturedQuery = captor.getValue();

        assertEquals(expectedDate, capturedQuery.date());
        assertEquals(Optional.empty(), capturedQuery.productId());
        assertEquals(Optional.empty(), capturedQuery.brandId());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void givenExistingProductIdFilter_whenGetAllPrices_thenMatchingPricesAreReturned() {
        // GIVEN
        PriceFilter filter = new PriceFilter(
                Optional.empty(),
                Optional.of(35455L),
                Optional.empty()
        );

        when(priceRepository.findPrices(any())).thenReturn(prices);

        // WHEN
        List<Price> result = priceService.findPrices(filter);

        // THEN
        ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
        verify(priceRepository, Mockito.times(1)).findPrices(any());
        verify(priceRepository).findPrices(captor.capture());

        PriceQuery capturedQuery = captor.getValue();

        assertEquals(Optional.empty(), capturedQuery.date());
        assertEquals(Optional.of(35455L), capturedQuery.productId());
        assertEquals(Optional.empty(), capturedQuery.brandId());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(prices, result);
    }

    @Test
    void givenNonExistingProductIdFilter_whenGetAllPrices_thenNoPricesAreReturned() {
        // GIVEN
        PriceFilter filter = new PriceFilter(
                Optional.empty(),
                Optional.of(35456L),
                Optional.empty()
        );

        when(priceRepository.findPrices(any())).thenReturn(List.of());

        // WHEN
        List<Price> result = priceService.findPrices(filter);

        // THEN
        ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
        verify(priceRepository, Mockito.times(1)).findPrices(any());
        verify(priceRepository).findPrices(captor.capture());

        PriceQuery capturedQuery = captor.getValue();

        assertEquals(Optional.empty(), capturedQuery.date());
        assertEquals(Optional.of(35456L), capturedQuery.productId());
        assertEquals(Optional.empty(), capturedQuery.brandId());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void givenExistingBrandIdFilter_whenGetAllPrices_thenMatchingPricesAreReturned() {
        // GIVEN
        PriceFilter filter = new PriceFilter(
                Optional.empty(),
                Optional.empty(),
                Optional.of(1L)
        );

        when(priceRepository.findPrices(any())).thenReturn(prices);

        // WHEN
        List<Price> result = priceService.findPrices(filter);

        // THEN
        ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
        verify(priceRepository, Mockito.times(1)).findPrices(any());
        verify(priceRepository).findPrices(captor.capture());

        PriceQuery capturedQuery = captor.getValue();

        assertEquals(Optional.empty(), capturedQuery.date());
        assertEquals(Optional.empty(), capturedQuery.productId());
        assertEquals(Optional.of(1L), capturedQuery.brandId());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(prices, result);
    }

    @Test
    void givenNonExistingBrandIdFilter_whenGetAllPrices_thenNoPricesAreReturned() {
        // GIVEN
        PriceFilter filter = new PriceFilter(
                Optional.empty(),
                Optional.empty(),
                Optional.of(2L)
        );

        when(priceRepository.findPrices(any())).thenReturn(prices);

        // WHEN
        List<Price> result = priceService.findPrices(filter);

        // THEN
        ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
        verify(priceRepository, Mockito.times(1)).findPrices(any());
        verify(priceRepository).findPrices(captor.capture());

        PriceQuery capturedQuery = captor.getValue();

        assertEquals(Optional.empty(), capturedQuery.date());
        assertEquals(Optional.empty(), capturedQuery.productId());
        assertEquals(Optional.of(2L), capturedQuery.brandId());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void givenAllExistingFilters_whenGetAllPrices_thenMatchingAndPriorityPricesAreReturned() {
        // GIVEN
        Optional<LocalDateTime> expectedDate = Optional
                .of(LocalDateTime.of(2020, 9, 20, 16, 24));

        PriceFilter filter = new PriceFilter(
                expectedDate,
                Optional.of(35455L),
                Optional.of(1L)
        );

        List<Price> expectedResult = prices.stream().filter(price -> price.priority().equals(1L)).toList();

        when(priceRepository.findPrices(any())).thenReturn(prices);

        // WHEN
        List<Price> result = priceService.findPrices(filter);

        // THEN
        ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
        verify(priceRepository, Mockito.times(1)).findPrices(any());
        verify(priceRepository).findPrices(captor.capture());

        PriceQuery capturedQuery = captor.getValue();

        assertEquals(expectedDate, capturedQuery.date());
        assertEquals(Optional.of(35455L), capturedQuery.productId());
        assertEquals(Optional.of(1L), capturedQuery.brandId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResult, result);
    }

}

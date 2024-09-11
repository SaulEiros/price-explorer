package com.sauleiros.priceexplorer.infrastructure.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sauleiros.priceexplorer.infrastructure.adapters.input.rest.models.RestPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PriceE2ETests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void day14_10AM_test() throws Exception {
        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        Long expectedBrandId = 1L;
        Long expectedProductId = 35455L;
        Double expectedPrice = 35.50;
        Long expectedPriceList = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices?date=2020-06-14T10:00:00&brandId=1&productId=35455")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        RestPrice price = objectMapper.readValue(result.getResponse().getContentAsString(), RestPrice.class);
        assertEquals(expectedProductId, price.productId());
        assertEquals(expectedBrandId, price.brandId());
        assertEquals(expectedPriceList, price.priceList());
        assertEquals(expectedPrice, price.price());
        assertEquals("EUR", price.currency());
        assertEquals(expectedStartDate, price.startDate());
        assertEquals(expectedEndDate, price.endDate());
    }

    @Test
    public void day14_16AM_test() throws Exception {
        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);
        Long expectedBrandId = 1L;
        Long expectedProductId = 35455L;
        Double expectedPrice = 25.45;
        Long expectedPriceList = 2L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices?date=2020-06-14T16:00:00&brandId=1&productId=35455")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        RestPrice price = objectMapper.readValue(result.getResponse().getContentAsString(), RestPrice.class);
        assertEquals(expectedProductId, price.productId());
        assertEquals(expectedBrandId, price.brandId());
        assertEquals(expectedPriceList, price.priceList());
        assertEquals(expectedPrice, price.price());
        assertEquals("EUR", price.currency());
        assertEquals(expectedStartDate, price.startDate());
        assertEquals(expectedEndDate, price.endDate());
    }

    @Test
    public void day14_21AM_test() throws Exception {
        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        Long expectedBrandId = 1L;
        Long expectedProductId = 35455L;
        Double expectedPrice = 35.50;
        Long expectedPriceList = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices?date=2020-06-14T21:00:00&brandId=1&productId=35455")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        RestPrice price = objectMapper.readValue(result.getResponse().getContentAsString(), RestPrice.class);
        assertEquals(expectedProductId, price.productId());
        assertEquals(expectedBrandId, price.brandId());
        assertEquals(expectedPriceList, price.priceList());
        assertEquals(expectedPrice, price.price());
        assertEquals("EUR", price.currency());
        assertEquals(expectedStartDate, price.startDate());
        assertEquals(expectedEndDate, price.endDate());
    }

    @Test
    public void day15_10AM_test() throws Exception {
        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 15, 0, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 6, 15, 11, 0, 0);
        Long expectedBrandId = 1L;
        Long expectedProductId = 35455L;
        Double expectedPrice = 30.50;
        Long expectedPriceList = 3L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices?date=2020-06-15T10:00:00&brandId=1&productId=35455")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        RestPrice price = objectMapper.readValue(result.getResponse().getContentAsString(), RestPrice.class);
        assertEquals(expectedProductId, price.productId());
        assertEquals(expectedBrandId, price.brandId());
        assertEquals(expectedPriceList, price.priceList());
        assertEquals(expectedPrice, price.price());
        assertEquals("EUR", price.currency());
        assertEquals(expectedStartDate, price.startDate());
        assertEquals(expectedEndDate, price.endDate());
    }

    @Test
    public void day16_21AM_test() throws Exception {
        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 15, 16, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        Long expectedBrandId = 1L;
        Long expectedProductId = 35455L;
        Double expectedPrice = 38.95;
        Long expectedPriceList = 4L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices?date=2020-06-16T21:00:00&brandId=1&productId=35455")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        RestPrice price = objectMapper.readValue(result.getResponse().getContentAsString(), RestPrice.class);
        assertEquals(expectedProductId, price.productId());
        assertEquals(expectedBrandId, price.brandId());
        assertEquals(expectedPriceList, price.priceList());
        assertEquals(expectedPrice, price.price());
        assertEquals("EUR", price.currency());
        assertEquals(expectedStartDate, price.startDate());
        assertEquals(expectedEndDate, price.endDate());
    }
}

package com.sauleiros.priceexplorer.infrastructure.adapters.input.rest;

import com.sauleiros.priceexplorer.application.ports.input.PriceService;
import com.sauleiros.priceexplorer.infrastructure.adapters.input.rest.models.RestPrice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Tag(name = "Prices")
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;


    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(summary = "Get a Prices. This price must match the provided date, brandId and productId" +
            "In case that more than one Price matches the parameters, the one with higher priority will be returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The matching Price.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestPrice.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameter provided.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Price not found.",
                    content = @Content),
    }
    )
    @GetMapping
    public RestPrice findPrices(
            @Parameter(description = "The date on which the price is elegible.")
            @RequestParam("date") LocalDateTime date,
            @Parameter(description = "The Brand Id of the desired Prices.")
            @RequestParam("brandId") Long brandId,
            @Parameter(description = "The Product Id of the desired Prices.")
            @RequestParam("productId") Long productId
    ) {
        return RestPrice.fromDomain(this.priceService.findPrice(date, brandId, productId));
    }
}

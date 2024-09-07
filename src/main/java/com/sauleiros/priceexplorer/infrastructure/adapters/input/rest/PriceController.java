package com.sauleiros.priceexplorer.infrastructure.adapters.input.rest;

import com.sauleiros.priceexplorer.application.ports.input.PriceService;
import com.sauleiros.priceexplorer.application.ports.input.filter.PriceFilter;
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
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Prices")
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;


    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(summary = "Get a list of Prices. " +
            "If filtered by date, Priorty Prices will be returned on overlaped results.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A List of Prices",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestPrice.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameter provided.",
                    content = @Content),
    }
    )
    @GetMapping
    public List<RestPrice> findPrices(
            @Parameter(description = "The date on which the price is elegible.")
            @RequestParam("date") Optional<LocalDateTime> dateTime,
            @Parameter(description = "The Brand Id of the desired Prices.")
            @RequestParam("brandId") Optional<Long> brandId,
            @Parameter(description = "The Product Id of the desired Prices.")
            @RequestParam("productId") Optional<Long> productId
    ) {
        PriceFilter priceFilter = new PriceFilter(
                dateTime,
                productId,
                brandId
        );

        return this.priceService.findPrices(priceFilter).stream().map(RestPrice::fromDomain).toList();
    }
}

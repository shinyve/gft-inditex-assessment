package com.example.assessment.infrastructure.controller;

import com.example.assessment.application.GetPriceByDateProductAndBrandUseCase;
import com.example.assessment.application.exception.PriceNotFoundException;
import com.example.assessment.infrastructure.controller.dto.Error;
import com.example.assessment.infrastructure.controller.dto.PriceResponse;
import com.example.assessment.infrastructure.controller.utils.Formatter;
import com.example.assessment.infrastructure.mapper.PriceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Price Controller", description = "Controller to return prices")
public class PriceController {
    private final PriceMapper priceMapper;
    private final GetPriceByDateProductAndBrandUseCase getPriceByDateProductAndBrandUseCase;

    @GetMapping("/prices")
    @Operation(summary = "Get Prices filtered by date, product id and brand id")
    @ApiResponse(responseCode = "200", description = "Price founded",
            content = @Content(schema = @Schema(implementation = PriceResponse.class)))
    @ApiResponse(responseCode = "404", description = "Price not founded")
    @ApiResponse(responseCode = "400", description = "Bad date format",
            content = @Content(schema = @Schema(implementation = Error.class)))
    public ResponseEntity getPrice(@RequestParam(value = "date") String date,
                                   @RequestParam(value = "productId") Long productId,
                                   @RequestParam(value = "brandId") Long brandId) {
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(date, Formatter.DATE_FORMATTER);
            return ResponseEntity.ok().body(priceMapper.fromDomainToDto(getPriceByDateProductAndBrandUseCase.execute(dateTime, productId, brandId)));
        } catch (DateTimeParseException dateTimeParseException) {
            return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, "Bad date format"));
        } catch (PriceNotFoundException priceNotFoundException) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong"));
        }
    }
}

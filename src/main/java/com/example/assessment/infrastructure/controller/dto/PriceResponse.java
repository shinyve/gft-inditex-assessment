package com.example.assessment.infrastructure.controller.dto;

import com.example.assessment.infrastructure.controller.utils.Formatter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class PriceResponse {

    @Schema(description = "ID of a product", example = "1")
    private Long productId;
    @Schema(description = "ID of a brand", example = "1")
    private Long brandId;
    @Schema(description = "Identification of the applicable price", example = "2")
    private Long priceList;
    @Schema(description = "Start date of the applicable price", example = "2020-06-14-00:00:00")
    private String startDate;
    @Schema(description = "End date of the applicable price", example = "2020-12-31-23:59:59")
    private String endDate;
    @Schema(description = "Final price", example = "35.50")
    private Double price;

    public PriceResponse(Long productId, Long brandId, Long priceList, LocalDateTime startDate, LocalDateTime endDate, Double price) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = dateFormatter(startDate);
        this.endDate = dateFormatter(endDate);
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getPriceList() {
        return priceList;
    }

    public void setPriceList(Long priceList) {
        this.priceList = priceList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private String dateFormatter(LocalDateTime localDateTime) {
        return localDateTime.format(Formatter.DATE_FORMATTER);
    }
}

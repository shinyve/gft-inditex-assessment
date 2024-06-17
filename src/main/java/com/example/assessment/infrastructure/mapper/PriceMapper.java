package com.example.assessment.infrastructure.mapper;

import com.example.assessment.infrastructure.controller.dto.PriceResponse;
import com.example.assessment.infrastructure.persistence.jpa.entity.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    public PriceResponse fromDomainToDto(com.example.assessment.domain.Price priceEntity) {

        return new PriceResponse(priceEntity.getProductId(),
                priceEntity.getBrandId(),
                priceEntity.getPriceList(),
                priceEntity.getStartDate(),
                priceEntity.getEndDate(),
                priceEntity.getPrice());
    }

    public com.example.assessment.domain.Price fromEntityToDomain(Price priceEntity) {
        return new com.example.assessment.domain.Price(priceEntity.getId(),
                priceEntity.getBrandId(),
                priceEntity.getStartDate(),
                priceEntity.getEndDate(),
                priceEntity.getPriceList(),
                priceEntity.getProductId(),
                priceEntity.getPriority(),
                priceEntity.getPrice(),
                priceEntity.getCurrency());
    }
}

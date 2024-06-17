package com.example.assessment.infrastructure.persistence.jpa;

import com.example.assessment.application.port.PriceRepository;
import com.example.assessment.domain.Price;
import com.example.assessment.infrastructure.mapper.PriceMapper;
import com.example.assessment.infrastructure.persistence.jpa.repository.PriceJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {
    private final PriceJPARepository priceJPARepository;
    private final PriceMapper priceMapper;

    @Override
    public ArrayList<Price> getPricesByDateAndProductIdAndBrandId(LocalDateTime date, Long productId, Long brandId) {
        return priceJPARepository.findByDateAndProductIdAndBrandId(date, productId, brandId)
                .stream()
                .map(priceMapper::fromEntityToDomain)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

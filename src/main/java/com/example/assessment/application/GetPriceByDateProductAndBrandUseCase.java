package com.example.assessment.application;

import com.example.assessment.application.exception.PriceNotFoundException;
import com.example.assessment.application.port.PriceRepository;
import com.example.assessment.domain.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GetPriceByDateProductAndBrandUseCase {
    private final PriceRepository priceRepository;

    public Price execute(LocalDateTime date, Long productId, Long brandId) {
        ArrayList<Price> prices = priceRepository.getPricesByDateAndProductIdAndBrandId(date, productId, brandId);
        if (!prices.isEmpty()) {
            //First result sorted by priority
            return prices.get(0);
        }
        throw new PriceNotFoundException("None price founded by given params");
    }
}

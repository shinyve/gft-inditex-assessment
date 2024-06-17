package com.example.assessment.application.port;

import com.example.assessment.domain.Price;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface PriceRepository {

    ArrayList<Price> getPricesByDateAndProductIdAndBrandId(LocalDateTime date, Long productId, Long brandId);
}

package com.example.assessment.application;

import com.example.assessment.application.exception.PriceNotFoundException;
import com.example.assessment.application.port.PriceRepository;
import com.example.assessment.domain.Price;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.times;

@SpringBootTest
public class GetPriceByDateProductAndBrandUseCaseTest {
    @InjectMocks
    private GetPriceByDateProductAndBrandUseCase getPriceByDateProductAndBrandUseCase;
    @Mock
    private PriceRepository priceRepository;

    private final LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");
    private final Long productId = 34567L;
    private final Long brandId = 1L;

    @Test
    void executeTest() {
        ArrayList<Price> pricesExpected = buildPricesDifferentPriority();
        when(priceRepository.getPricesByDateAndProductIdAndBrandId(date, productId, brandId))
                .thenReturn(pricesExpected);

        Price priceResult = getPriceByDateProductAndBrandUseCase.execute(date, productId, brandId);

        assertNotNull(priceResult);
        assertEquals(pricesExpected.get(0).getPrice(), priceResult.getPrice());
        assertEquals(pricesExpected.get(0).getPriceList(), priceResult.getPriceList());

        then(priceRepository).should(times(1))
                .getPricesByDateAndProductIdAndBrandId(date, productId, brandId);
    }

    @Test
    void executeNotPricesFound() {
        when(priceRepository.getPricesByDateAndProductIdAndBrandId(date, productId, brandId))
                .thenReturn(new ArrayList<>());

        assertThrows(PriceNotFoundException.class,
                () -> getPriceByDateProductAndBrandUseCase.execute(date, productId, brandId));

        then(priceRepository).should(times(1))
                .getPricesByDateAndProductIdAndBrandId(date, productId, brandId);
    }

    private ArrayList<Price> buildPricesDifferentPriority() {
        ArrayList<Price> pricesExpected = new ArrayList<>();
        LocalDateTime startDate = LocalDateTime.parse("2020-06-14T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-12-31T23:59:59");
        Price pricePriority1 = new Price(1L,
                1l,
                startDate,
                endDate,
                1L,
                34567L,
                1,
                34.5D,
                "EUR");

        Price pricePriority0 = new Price(1L,
                1l,
                startDate,
                endDate,
                1L,
                34567L,
                0,
                37.5D,
                "EUR");

        pricesExpected.add(pricePriority1);
        pricesExpected.add(pricePriority0);

        return pricesExpected;

    }
}

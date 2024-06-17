package com.example.assessment.infrastructure.controller;

import com.example.assessment.application.GetPriceByDateProductAndBrandUseCase;
import com.example.assessment.application.exception.PriceNotFoundException;
import com.example.assessment.infrastructure.mapper.PriceMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.times;

@SpringBootTest
public class PriceControllerTest {
    @InjectMocks
    private PriceController priceController;
    @Mock
    private PriceMapper priceMapper;
    @Mock
    private GetPriceByDateProductAndBrandUseCase getPriceByDateProductAndBrandUseCase;

    @Test
    void getPriceDateWrongFormatTest() {
        String date = "2020-06-14 10:00:00";
        Long productId = 34567L;
        Long brandId = 1L;
        ResponseEntity responseEntity = priceController.getPrice(date, productId, brandId);

        assertTrue(responseEntity.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(400)));

        then(getPriceByDateProductAndBrandUseCase).shouldHaveNoInteractions();
        then(priceMapper).shouldHaveNoInteractions();
    }

    @Test
    void getPricePriceNotFoundTest() {
        String date = "2020-06-14-10:00:00";
        Long productId = 34567L;
        Long brandId = 1L;

        when(getPriceByDateProductAndBrandUseCase.execute(any(), any(), any()))
                .thenThrow(PriceNotFoundException.class);
        ResponseEntity responseEntity = priceController.getPrice(date, productId, brandId);

        assertTrue(responseEntity.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404)));

        then(getPriceByDateProductAndBrandUseCase).should(times(1))
                .execute(any(), any(), any());
        then(priceMapper).shouldHaveNoInteractions();
    }


}

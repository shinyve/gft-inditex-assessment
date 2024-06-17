package com.example.assessment.integration;

import com.example.assessment.infrastructure.controller.dto.PriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetPriceByDateProductAndBrandTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest
    @CsvSource({
            "2020-06-14-10:00:00, 35455, 1, 35.50, 1, 2020-06-14-00:00:00, 2020-12-31-23:59:59",
            "2020-06-14-16:00:00, 35455, 1, 25.45, 2, 2020-06-14-15:00:00, 2020-06-14-18:30:00",
            "2020-06-14-10:00:00, 35455, 1, 35.50, 1, 2020-06-14-00:00:00, 2020-12-31-23:59:59",
            "2020-06-15-10:00:00, 35455, 1, 30.50, 3, 2020-06-15-00:00:00, 2020-06-15-11:00:00",
            "2020-06-16-21:00:00, 35455, 1, 38.95, 4, 2020-06-15-16:00:00, 2020-12-31-23:59:59"
    })
    void getPriceTest(String date,
                      Long productId,
                      Long brandId,
                      Double priceExpected,
                      Long priceListExpected,
                      String startDateExpected,
                      String endDateExpected) {

        String url = "/prices?date=" + date + "&productId=" + productId + "&brandId=" +brandId;
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        PriceResponse price = response.getBody();
        assertNotNull(price);
        assertEquals(productId, price.getProductId());
        assertEquals(priceExpected, price.getPrice());
        assertEquals(priceListExpected, price.getPriceList());
        assertEquals(startDateExpected, price.getStartDate());
        assertEquals(endDateExpected, price.getEndDate());
    }

    @Test
    void getPriceBadDateFormat() {
        String url = "/prices?date=2020-06-14 10:00:00&productId=35455&brandId=1";
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void getPriceMissingParams() {
        String url = "/prices?date=2020-06-14 10:00:00&productId=35455";
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

}

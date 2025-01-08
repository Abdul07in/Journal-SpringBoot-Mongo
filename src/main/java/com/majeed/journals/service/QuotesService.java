package com.majeed.journals.service;

import com.majeed.journals.api.response.QuoteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class QuotesService {

    @Value("${quotes.api.url}")
    private String BASE_URL;

    private final RestTemplate restTemplate;

    public QuotesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getQuotes() {
        ResponseEntity<QuoteResponse> responseEntity = restTemplate.exchange(BASE_URL + "/quotes", HttpMethod.GET, null, QuoteResponse.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            QuoteResponse response = responseEntity.getBody();
            Random random = new Random();
            QuoteResponse.Quote quote1 = response.getQuotes().get(random.nextInt(response.getQuotes().size() - 1));
            String quote = quote1.getQuote() +" By " + quote1.getAuthor();
            return quote;
        }
        return null;
    }
}

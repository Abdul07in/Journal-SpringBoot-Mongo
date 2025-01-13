package com.majeed.journals.service;

import com.majeed.journals.api.response.QuoteResponse;
import org.springframework.beans.factory.annotation.Value;
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
    private final RedisService redisService;

    public QuotesService(RestTemplate restTemplate, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }

    public String getQuotes() {
        QuoteResponse quotes = redisService.get("quotes", QuoteResponse.class);
        if (quotes != null) {
            Random random = new Random();
            QuoteResponse.Quote quote1 = quotes.getQuotes().get(random.nextInt(quotes.getQuotes().size() - 1));
            String quote = quote1.getQuote() + " By " + quote1.getAuthor();
            return quote;
        }
        ResponseEntity<QuoteResponse> responseEntity = restTemplate.exchange(BASE_URL + "/quotes?limit=50", HttpMethod.GET, null, QuoteResponse.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            QuoteResponse response = responseEntity.getBody();
            redisService.set("quotes", response, 1200L);
            Random random = new Random();
            QuoteResponse.Quote quote1 = response.getQuotes().get(random.nextInt(response.getQuotes().size() - 1));
            String quote = quote1.getQuote() + " By " + quote1.getAuthor();
            return quote;
        }
        return null;
    }
}

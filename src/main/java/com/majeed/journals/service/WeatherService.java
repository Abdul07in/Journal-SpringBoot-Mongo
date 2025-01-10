package com.majeed.journals.service;

import com.majeed.journals.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String API_KEY;
    private static final String BASE_URL = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    private final RestTemplate restTemplate;
    private final RedisService redisService;

    public WeatherService(RestTemplate restTemplate, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }


    public WeatherResponse getWeather(String city) {
        redisService.get("weather:" + city , WeatherResponse.class);
        String url = BASE_URL.replace("API_KEY", API_KEY).replace("CITY", city);
        ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
        return responseEntity.getBody();
    }

}

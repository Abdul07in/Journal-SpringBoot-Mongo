package com.majeed.journals.service;

import com.majeed.journals.api.response.WeatherResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String API_KEY = "ba9f80960815791caa42e03930c0613d";
    private static final String BASE_URL = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getWeather(String city) {
        String url = BASE_URL.replace("API_KEY", API_KEY).replace("CITY", city);
        restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class).getBody();
        return null;
    }

}

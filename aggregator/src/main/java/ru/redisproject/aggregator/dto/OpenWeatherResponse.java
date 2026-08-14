package ru.redisproject.aggregator.dto;

import lombok.Data;

@Data
public class OpenWeatherResponse {
    private String name;
    private MainData main;
    private WindData wind;
    private WeatherDescription[] weather;
}

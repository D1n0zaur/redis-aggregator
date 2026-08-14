package ru.redisproject.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRequest {
    private String city;
    private Double temperature;
    private Double feelsLike;
    private Integer humidity;
    private Integer pressure;
    private Double windSpeed;
    private String weatherCondition;
    private String iconCode;
}

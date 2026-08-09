package ru.redisproject.aggregator.mapper;

import org.springframework.stereotype.Component;
import ru.redisproject.aggregator.dto.WeatherRequest;
import ru.redisproject.aggregator.dto.WeatherResponse;
import ru.redisproject.aggregator.entity.WeatherRecord;

import java.time.format.DateTimeFormatter;

@Component
public class WeatherMapper {

    public WeatherRecord toEntity(WeatherRequest request) {
        return WeatherRecord.builder()
                .city(request.getCity())
                .temperature(request.getTemperature())
                .feelsLike(request.getFeelsLike())
                .humidity(request.getHumidity())
                .pressure(request.getPressure())
                .windSpeed(request.getWindSpeed())
                .weatherCondition(request.getWeatherCondition())
                .build();
    }

    public WeatherResponse toResponse(WeatherRecord entity) {
        return WeatherResponse.builder()
                .city(entity.getCity())
                .temperature(entity.getTemperature())
                .feelsLike(entity.getFeelsLike())
                .humidity(entity.getHumidity())
                .pressure(entity.getPressure())
                .windSpeed(entity.getWindSpeed())
                .weatherCondition(entity.getWeatherCondition())
                .timestamp(entity.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

}

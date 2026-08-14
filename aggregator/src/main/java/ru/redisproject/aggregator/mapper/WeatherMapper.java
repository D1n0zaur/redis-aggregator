package ru.redisproject.aggregator.mapper;

import org.springframework.stereotype.Component;
import ru.redisproject.aggregator.dto.OpenWeatherResponse;
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
                .iconCode(request.getIconCode())
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
                .iconCode(entity.getIconCode())
                .build();
    }

    public WeatherRequest toWeatherRequest(OpenWeatherResponse response) {
        return WeatherRequest.builder()
                .city(response.getName())
                .temperature(response.getMain().getTemp())
                .feelsLike(response.getMain().getFeels_like())
                .humidity(response.getMain().getHumidity())
                .pressure(response.getMain().getPressure())
                .windSpeed(
                        response.getWind() != null ? response.getWind().getSpeed() : null
                )
                .weatherCondition(
                        response.getWeather() != null && response.getWeather().length > 0
                            ? response.getWeather()[0].getDescription() : null
                )
                .iconCode(
                        response.getWeather() != null && response.getWeather().length > 0
                                ? response.getWeather()[0].getIcon()
                                : null
                )
                .build();
    }

}

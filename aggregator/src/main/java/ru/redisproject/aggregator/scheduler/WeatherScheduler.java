package ru.redisproject.aggregator.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.redisproject.aggregator.client.OpenWeatherClient;
import ru.redisproject.aggregator.dto.WeatherRequest;
import ru.redisproject.aggregator.service.WeatherService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherScheduler {
    private final OpenWeatherClient weatherClient;
    private final WeatherService weatherService;

    private static final List<String> CITIES = List.of("Moscow", "London", "Tokyo");

    @Scheduled(cron = "0 0/15 * * * *")
    public void fetchWeatherForCities() {
        log.info("Starting scheduled weather fetch for {} cities", CITIES.size());

        for (String city : CITIES) {
            try {
                WeatherRequest request = weatherClient.fetchWeather(city);
                weatherService.save(request);
                log.info("Successfully saved weather for city: {}", city);
            } catch (Exception e) {
                log.error("Failed to fetch or save weather for city: {}", city, e);
            }
        }
        log.info("Finished scheduled weather fetch");
    }
}

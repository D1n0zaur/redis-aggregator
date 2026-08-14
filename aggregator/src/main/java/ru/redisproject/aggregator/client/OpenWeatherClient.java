package ru.redisproject.aggregator.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.redisproject.aggregator.dto.OpenWeatherResponse;
import ru.redisproject.aggregator.dto.WeatherRequest;
import ru.redisproject.aggregator.mapper.WeatherMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherClient {
    private final RestClient restClient;
    private final WeatherMapper mapper;

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherRequest fetchWeather(String city) {
        log.info("Fetching weather for city: {}", city);

        String apiKey = System.getenv("OPENWEATHER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("OpenWeather API key is not set in environment variables");
        }

        String fullUrl = API_URL + "?q=" + city + "&appid=" + apiKey + "&units=metric";

        OpenWeatherResponse response = restClient.get()
                .uri(fullUrl)
                .retrieve()
                .body(OpenWeatherResponse.class);

        if (response == null) {
            throw new RuntimeException("Empty response from OpenWeather for city: " + city);
        }

        return mapper.toWeatherRequest(response);
    }
}

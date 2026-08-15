package ru.redisproject.aggregator.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.redisproject.aggregator.dto.WeatherRequest;
import ru.redisproject.aggregator.dto.WeatherResponse;
import ru.redisproject.aggregator.entity.WeatherRecord;
import ru.redisproject.aggregator.mapper.WeatherMapper;
import ru.redisproject.aggregator.repository.WeatherRecordRepository;
import ru.redisproject.aggregator.service.cache.RedisCacheService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRecordRepository repository;
    private final WeatherMapper mapper;
    private final RedisCacheService cacheService;

    @Transactional
    public WeatherResponse save(WeatherRequest request) {
        WeatherRecord entity = mapper.toEntity(request);
        WeatherRecord saved = repository.save(entity);
        WeatherResponse response = mapper.toResponse(saved);
        log.info("Saved weather record for city: {}", request.getCity());

        cacheService.saveWeather(request.getCity(), response);

        return response;
    }

    public List<WeatherResponse> getLast5(String city) {
        return repository.findTop5ByCityOrderByCreatedAtDesc(city)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public WeatherResponse getLast(String city) {
        return cacheService.getWeather(city)
                .orElseGet(() -> {
                    WeatherRecord entity = repository.findFirstByCityOrderByCreatedAtDesc(city);
                    if (entity == null) {
                        return null;
                    }
                    WeatherResponse response = mapper.toResponse(entity);
                    cacheService.saveWeather(city, response);

                    return response;
                });
    }
}

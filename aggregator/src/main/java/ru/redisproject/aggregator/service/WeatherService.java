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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRecordRepository repository;
    private final WeatherMapper mapper;

    @Transactional
    public WeatherResponse save(WeatherRequest request) {
        WeatherRecord entity = mapper.toEntity(request);
        WeatherRecord saved = repository.save(entity);
        log.info("Saved weather record for city: {}", request.getCity());

        return mapper.toResponse(saved);
    }

    public List<WeatherResponse> getLast5(String city) {
        return repository.findTop5ByCityOrderByCreatedAtDesc(city)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public WeatherResponse getLast(String city) {
        WeatherRecord entity = repository.findFirstByCityOrderByCreatedAtDesc(city);
        return entity != null ? mapper.toResponse(entity) : null;
    }
}

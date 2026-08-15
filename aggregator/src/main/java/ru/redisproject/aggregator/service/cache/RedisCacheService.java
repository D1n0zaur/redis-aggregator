package ru.redisproject.aggregator.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.redisproject.aggregator.dto.WeatherResponse;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final Duration TTL = Duration.ofMinutes(15);
    private static final String KEY_PREFIX = "weather:";

    public void saveWeather(String city, WeatherResponse weather) {
        try {
            String key = KEY_PREFIX + city;
            String json = objectMapper.writeValueAsString(weather);
            redisTemplate.opsForValue().set(key, json, TTL);
            log.debug("Cached weather for city: {}", city);
        } catch (Exception e) {
            log.error("Failed to cache weather for city: {}", city, e);
        }
    }

    public Optional<WeatherResponse> getWeather(String city) {
        try {
            String key = KEY_PREFIX + city;
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                return Optional.empty();
            }
            WeatherResponse weather = objectMapper.readValue(json, WeatherResponse.class);
            log.debug("Cache hit for city: {}", city);
            return Optional.of(weather);
        } catch (Exception e) {
            log.warn("Cache miss or error for city: {}", city, e);
            return Optional.empty();
        }
    }

    public void evictCache(String city) {
        try {
            String key = KEY_PREFIX + city;
            redisTemplate.delete(key);
            log.debug("Evicted cache for city: {}", city);
        } catch (Exception e) {
            log.error("Failed to evict cache for city: {}", city, e);
        }
    }

}

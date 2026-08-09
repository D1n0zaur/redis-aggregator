package ru.redisproject.aggregator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redisproject.aggregator.dto.WeatherRequest;
import ru.redisproject.aggregator.dto.WeatherResponse;
import ru.redisproject.aggregator.service.WeatherService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

    private final WeatherService service;

    @PostMapping
    public ResponseEntity<WeatherResponse> addRecord(@RequestBody WeatherRequest request) {
        WeatherResponse saved = service.save(request);
        log.info("Received request to add weather record: {}", request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{city}/history")
    public ResponseEntity<List<WeatherResponse>> getHistory(@PathVariable String city) {
        log.info("Received request to get history for city: {}", city);


        return ResponseEntity.ok(service.getLast5(city));
    }

    @GetMapping("/{city}/current")
    public ResponseEntity<WeatherResponse> getCurrent(@PathVariable String city) {
        WeatherResponse response = service.getLast(city);
        log.info("Received request to get current weather for city: {}", city);


        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

}

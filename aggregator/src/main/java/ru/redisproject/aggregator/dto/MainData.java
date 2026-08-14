package ru.redisproject.aggregator.dto;

import lombok.Data;

@Data
public class MainData {
    private Double temp;
    private Double feels_like;
    private Integer humidity;
    private Integer pressure;
}

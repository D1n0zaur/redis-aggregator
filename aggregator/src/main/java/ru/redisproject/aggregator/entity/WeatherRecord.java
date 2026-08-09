package ru.redisproject.aggregator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "weather_records",
        indexes = {
                @Index(columnList = "city"),
                @Index(columnList = "createdAt DESC"),
                @Index(columnList = "city, createdAt DESC")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Double feelsLike;

    @Column(nullable = false)
    private Integer humidity;

    @Column(nullable = false)
    private Integer pressure;

    @Column
    private Double windSpeed;

    @Column(nullable = false)
    private String weatherCondition;

    @Column(length = 10)
    private String iconCode;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

package ru.redisproject.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.redisproject.aggregator.entity.WeatherRecord;

import java.util.List;

@Repository
public interface WeatherRecordRepository extends JpaRepository<WeatherRecord, Long> {
    List<WeatherRecord> findTop5ByCityOrderByCreatedAtDesc(String city);

    WeatherRecord findFirstByCityOrderByCreatedAtDesc(String city);
}

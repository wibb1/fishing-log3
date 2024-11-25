package com.fishingLog.spring.service;

import com.fishingLog.spring.exception.DataConversionException;
import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.WeatherRepository;
import com.fishingLog.spring.utils.StormGlassWeatherConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private WeatherRepository weatherRepository;
    private StormGlassWeatherConverter weatherConverter;

    public WeatherService(WeatherRepository weatherRepository, StormGlassWeatherConverter weatherConverter) {
        this.weatherConverter = weatherConverter;
        this.weatherRepository = weatherRepository;
    }

    public List<Weather> findAllWeather() {
        logger.info("Fetching all weather records");
        return weatherRepository.findAll();
    }

    public Optional<Weather> findWeatherById(Long id) {
        validateWeatherId(id);
        logger.info("Finding weather record by ID: {}", id);
        return weatherRepository.findById(id);
    }


    public Optional<Weather> findEqualWeather(Weather weather) {
        validateWeather(weather);
        logger.info("Finding weather record matching example: {}", weather);
        return weatherRepository.findOne(Example.of(weather));
    }

    public Weather saveWeather(Weather weather) {
        validateWeather(weather);
        logger.info("Saving weather record: {}", weather);
        return findEqualWeather(weather).orElseGet(() -> weatherRepository.save(weather));
    }

    public void deleteWeather(Long id) {
        validateWeatherId(id);
        if (!weatherRepository.existsById(id)) {
            logger.error("Weather record with ID {} does not exist", id);
            throw new IllegalArgumentException("Weather record with ID " + id + " does not exist");
        }
        logger.info("Deleting weather record with ID: {}", id);
        weatherRepository.deleteById(id);
    }

    public Weather createWeather(String weatherRawData) {
        if (weatherRawData == null || weatherRawData.isBlank()) {
            logger.error("Weather raw data cannot be null or blank");
            throw new IllegalArgumentException("Weather raw data cannot be null or blank");
        }
        Map<String, Object> weatherData;
        try {
            weatherData = weatherConverter.dataConverter(weatherRawData);
        } catch (IOException e) {
            logger.error("Error converting weather data, Error {}", e);
            throw new DataConversionException("Error converting weather data", e);
        }

        Weather newWeather = new Weather(weatherData);
        logger.info("Creating new weather record: {}", newWeather);
        return saveWeather(newWeather);
    }

    public void setWeatherRepository(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void setWeatherConverter(StormGlassWeatherConverter weatherConverter) {
        this.weatherConverter = weatherConverter;
    }

    private void validateWeather(Weather weather) {
        if (weather == null) {
            logger.error("Weather object cannot be null");
            throw new IllegalArgumentException("Weather object cannot be null");
        }
    }

    private void validateWeatherId(Long id) {
        if (id == null) {
            logger.error("Weather ID cannot be null");
            throw new IllegalArgumentException("Weather ID cannot be null");
        }
    }

}

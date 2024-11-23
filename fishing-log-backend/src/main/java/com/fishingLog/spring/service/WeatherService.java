package com.fishingLog.spring.service;

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
        return weatherRepository.findAll();
    }

    public Optional<Weather> findWeatherById(Long id) {
        return weatherRepository.findById(id);
    }


    public Optional<Weather> findEqualWeather(Weather weather) {
        if (weather == null) {
            logger.warn("Weather object is null");
            return Optional.empty();
        }
        return weatherRepository.findOne(Example.of(weather));
    }

    public Weather saveWeather(Weather weather) {
        return findEqualWeather(weather).orElseGet(() -> weatherRepository.save(weather));
    }

    public void deleteWeather(Long id) {
        weatherRepository.deleteById(id);
    }

    public Weather createWeather(String weatherRawData) {
        Map<String, Object> weatherData;
        try {
            weatherData = weatherConverter.dataConverter(weatherRawData);
        } catch (IOException e) {
            logger.error("Error converting weather data, Error {}", e);
            throw new RuntimeException("Error converting weather data", e);
        }

        Weather newWeather = new Weather(weatherData);
        return saveWeather(newWeather);
    }

    public void setWeatherRepository(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void setWeatherConverter(StormGlassWeatherConverter weatherConverter) {
        this.weatherConverter = weatherConverter;
    }
}

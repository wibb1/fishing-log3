package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.WeatherRepository;
import com.fishingLog.spring.utils.StormGlassWeatherConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class WeatherService {
    @Autowired
    public WeatherRepository weatherRepository;
    private final StormGlassWeatherConverter weatherConverter = new StormGlassWeatherConverter();

    public List<Weather> findAllWeather() {
        return weatherRepository.findAll();
    }

    public Optional<Weather> findWeatherById(Long id) {
        return weatherRepository.findById(id);
    }


    public Optional<Weather> findEqualWeather(Weather weather) {
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
            throw new RuntimeException("Error converting weather data", e);// TODO - logger to record error
        }

        Weather newWeather = new Weather(weatherData);
        return saveWeather(newWeather);
    }
}

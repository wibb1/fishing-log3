package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class WeatherService {
    @Autowired
    public WeatherRepository weatherRepository;

    public List<Weather> findAllWeather() {
        return weatherRepository.findAll();
    }

    public Optional<Weather> findWeather(Long id) {
        return weatherRepository.findById(id);
    }


    public Optional<Weather> findEqualWeather(Weather weather) {
        return weatherRepository.findOne(Example.of(weather));
    }

    public Weather saveWeather(Weather weather) {
        Optional<Weather> equalWeather = findEqualWeather(weather);
        return equalWeather.orElseGet(() -> weatherRepository.save(weather));
    }

    public void deleteWeather(Long id) {
        weatherRepository.deleteById(id);
    }
}

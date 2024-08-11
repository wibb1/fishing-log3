package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class WeatherService {
        @Autowired
        public WeatherRepository weatherRepository;

        public List<Weather> findAllRecords() {
            return weatherRepository.findAll();
        }

        public Optional<Weather> findWeather(Long id) {
            return weatherRepository.findById(id);
        }

        public Weather saveWeather(Weather tide) {
            return weatherRepository.save(tide);
        }

        public void deleteWeather(Long id) {
            weatherRepository.deleteById(id);
        }
}

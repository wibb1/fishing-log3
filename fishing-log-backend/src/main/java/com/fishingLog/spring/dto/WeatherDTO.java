// WeatherDTO.java
package com.fishingLog.spring.dto;

import com.fishingLog.spring.model.Weather;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class WeatherDTO {
    private Long id;
    private Double airTemperature;
    private Double cloudCover;
    private Double currentDirection;
    private Double currentSpeed;
    private Double gust;
    private Double humidity;
    private Double pressure;
    private Double seaLevel;
    private Double secondarySwellDirection;
    private Double secondarySwellHeight;
    private Double secondarySwellPeriod;
    private Double swellDirection;
    private Double swellHeight;
    private Double swellPeriod;
    private Instant time;
    private Double visibility;
    private Double waveDirection;
    private Double waveHeight;
    private Double wavePeriod;
    private Double windDirection;
    private Double windSpeed;
    private Double windWaveDirection;
    private Double windWaveHeight;
    private Double windWavePeriod;

    WeatherDTO(Weather weather) {
        this.id = weather.getId();
        this.airTemperature = weather.getAirTemperature();
        this.cloudCover = weather.getCloudCover();
        this.currentDirection = weather.getCurrentDirection();
        this.currentSpeed = weather.getCurrentSpeed();
        this.gust = weather.getGust();
        this.humidity = weather.getHumidity();
        this.pressure = weather.getPressure();
        this.seaLevel = weather.getSeaLevel();
        this.secondarySwellDirection = weather.getSecondarySwellDirection();
        this.secondarySwellHeight = weather.getSecondarySwellHeight();
        this.secondarySwellPeriod = weather.getSecondarySwellPeriod();
        this.swellDirection = weather.getSwellDirection();
        this.swellHeight = weather.getSwellHeight();
        this.swellPeriod = weather.getSwellPeriod();
        this.time = weather.getTime();
        this.visibility = weather.getVisibility();
        this.waveDirection = weather.getWaveDirection();
        this.waveHeight = weather.getWaveHeight();
        this.wavePeriod = weather.getWavePeriod();
        this.windDirection = weather.getWindDirection();
        this.windSpeed = weather.getWindSpeed();
        this.windWaveDirection = weather.getWindWaveDirection();
        this.windWaveHeight = weather.getWindWaveHeight();
        this.windWavePeriod = weather.getWindWavePeriod();
    }
}

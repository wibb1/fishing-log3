package com.fishingLog.spring.model;

import com.fishingLog.spring.utils.Conversions;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "weather")
@Setter
@Getter
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private Double airTemperature;
    @Column
    private Double cloudCover;
    @Column
    private Double currentDirection;
    @Column
    private Double currentSpeed;
    @Column
    private Double gust;
    @Column
    private Double humidity;
    @Column
    private Double pressure;
    @Column
    private Double seaLevel;
    @Column
    private Double secondarySwellDirection;
    @Column
    private Double secondarySwellHeight;
    @Column
    private Double secondarySwellPeriod;
    @Column
    private Double swellDirection;
    @Column
    private Double swellHeight;
    @Column
    private Double swellPeriod;
    @Column
    private Instant time;
    @Column
    private Double visibility;
    @Column
    private Double waveDirection;
    @Column
    private Double waveHeight;
    @Column
    private Double wavePeriod;
    @Column
    private Double windDirection;
    @Column
    private Double windSpeed;
    @Column
    private Double windWaveDirection;
    @Column
    private Double windWaveHeight;
    @Column
    private Double windWavePeriod;

    @OneToOne(mappedBy = "weather")
    private Record record;

    public void setTimeWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }
}

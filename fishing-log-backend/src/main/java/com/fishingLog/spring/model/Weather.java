package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fishingLog.spring.utils.Conversions;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "weather")
@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
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

    @OneToMany(mappedBy = "weather", orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    public void setTimeWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public Weather(JsonNode node) {
        this.airTemperature = node.get("airTemperature").asDouble();
        this.cloudCover = node.get("cloudCover").asDouble();
        this.currentDirection = node.get("currentDirection").asDouble();
        this.currentSpeed = node.get("currentSpeed").asDouble();
        this.gust = node.get("gust").asDouble();
        this.humidity = node.get("humidity").asDouble();
        this.pressure = node.get("pressure").asDouble();
        this.seaLevel = node.get("seaLevel").asDouble();
        this.secondarySwellDirection = node.get("secondarySwellDirection").asDouble();
        this.secondarySwellHeight = node.get("secondarySwellHeight").asDouble();
        this.secondarySwellPeriod = node.get("secondarySwellPeriod").asDouble();
        this.swellDirection = node.get("swellDirection").asDouble();
        this.swellHeight = node.get("swellHeight").asDouble();
        this.swellPeriod = node.get("swellPeriod").asDouble();
        this.time = Instant.parse(node.get("time").asText());
        this.visibility = node.get("visibility").asDouble();
        this.waveDirection = node.get("waveDirection").asDouble();
        this.waveHeight = node.get("waveHeight").asDouble();
        this.wavePeriod = node.get("wavePeriod").asDouble();
        this.windDirection = node.get("windDirection").asDouble();
        this.windSpeed = node.get("windSpeed").asDouble();
        this.windWaveDirection = node.get("windWaveDirection").asDouble();
        this.windWaveHeight = node.get("windWaveHeight").asDouble();
        this.windWavePeriod = node.get("windWavePeriod").asDouble();
    }

    public Weather(Map<String, Object> node) {
        this.airTemperature = (Double) node.get("airTemperature");
        this.cloudCover = (Double) node.get("cloudCover");
        this.currentDirection = (Double) node.get("currentDirection");
        this.currentSpeed = (Double) node.get("currentSpeed");
        this.gust = (Double) node.get("gust");
        this.humidity = (Double) node.get("humidity");
        this.pressure = (Double) node.get("pressure");
        this.seaLevel = (Double) node.get("seaLevel");
        this.secondarySwellDirection = (Double) node.get("secondarySwellDirection");
        this.secondarySwellHeight = (Double) node.get("secondarySwellHeight");
        this.secondarySwellPeriod = (Double) node.get("secondarySwellPeriod");
        this.swellDirection = (Double) node.get("swellDirection");
        this.swellHeight = (Double) node.get("swellHeight");
        this.swellPeriod = (Double) node.get("swellPeriod");
        this.time = (Instant) node.get("time");
        this.visibility = (Double) node.get("visibility");
        this.waveDirection = (Double) node.get("waveDirection");
        this.waveHeight = (Double) node.get("waveHeight");
        this.wavePeriod = (Double) node.get("wavePeriod");
        this.windDirection = (Double) node.get("windDirection");
        this.windSpeed = (Double) node.get("windSpeed");
        this.windWaveDirection = (Double) node.get("windWaveDirection");
        this.windWaveHeight = (Double) node.get("windWaveHeight");
        this.windWavePeriod = (Double) node.get("windWavePeriod");
    }
}

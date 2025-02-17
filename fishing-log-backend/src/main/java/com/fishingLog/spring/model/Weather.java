package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fishingLog.spring.utils.Conversions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "weather")
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @EqualsAndHashCode.Include
    @Column
    private Double airTemperature;
    @EqualsAndHashCode.Include
    @Column
    private Double cloudCover;
    @EqualsAndHashCode.Include
    @Column
    private Double currentDirection;
    @EqualsAndHashCode.Include
    @Column
    private Double currentSpeed;
    @EqualsAndHashCode.Include
    @Column
    private Double gust;
    @EqualsAndHashCode.Include
    @Column
    private Double humidity;
    @EqualsAndHashCode.Include
    @Column
    private Double pressure;
    @EqualsAndHashCode.Include
    @Column
    private Double seaLevel;
    @EqualsAndHashCode.Include
    @Column
    private Double secondarySwellDirection;
    @EqualsAndHashCode.Include
    @Column
    private Double secondarySwellHeight;
    @EqualsAndHashCode.Include
    @Column
    private Double secondarySwellPeriod;
    @EqualsAndHashCode.Include
    @Column
    private Double swellDirection;
    @EqualsAndHashCode.Include
    @Column
    private Double swellHeight;
    @EqualsAndHashCode.Include
    @Column
    private Double swellPeriod;
    @EqualsAndHashCode.Include
    @Column
    private Instant time;
    @EqualsAndHashCode.Include
    @Column
    private Double visibility;
    @EqualsAndHashCode.Include
    @Column
    private Double waveDirection;
    @EqualsAndHashCode.Include
    @Column
    private Double waveHeight;
    @EqualsAndHashCode.Include
    @Column
    private Double wavePeriod;
    @EqualsAndHashCode.Include
    @Column
    private Double windDirection;
    @EqualsAndHashCode.Include
    @Column
    private Double windSpeed;
    @EqualsAndHashCode.Include
    @Column
    private Double windWaveDirection;
    @EqualsAndHashCode.Include
    @Column
    private Double windWaveHeight;
    @EqualsAndHashCode.Include
    @Column
    private Double windWavePeriod;

    @OneToMany(mappedBy = "weather", orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    public void setTimeWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public Weather(JsonNode node) {
        this.airTemperature = node.get("airTemperature").get("sg").asDouble();
        this.cloudCover = node.get("cloudCover").get("sg").asDouble();
        this.currentDirection = node.get("currentDirection").get("sg").asDouble();
        this.currentSpeed = node.get("currentSpeed").get("sg").asDouble();
        this.gust = node.get("gust").get("sg").asDouble();
        this.humidity = node.get("humidity").get("sg").asDouble();
        this.pressure = node.get("pressure").get("sg").asDouble();
        this.seaLevel = node.get("seaLevel").get("sg").asDouble();
        this.secondarySwellDirection = node.get("secondarySwellDirection").get("sg").asDouble();
        this.secondarySwellHeight = node.get("secondarySwellHeight").get("sg").asDouble();
        this.secondarySwellPeriod = node.get("secondarySwellPeriod").get("sg").asDouble();
        this.swellDirection = node.get("swellDirection").get("sg").asDouble();
        this.swellHeight = node.get("swellHeight").get("sg").asDouble();
        this.swellPeriod = node.get("swellPeriod").get("sg").asDouble();
        this.time = Instant.parse(node.get("time").asText());
        this.visibility = node.get("visibility").get("sg").asDouble();
        this.waveDirection = node.get("waveDirection").get("sg").asDouble();
        this.waveHeight = node.get("waveHeight").get("sg").asDouble();
        this.wavePeriod = node.get("wavePeriod").get("sg").asDouble();
        this.windDirection = node.get("windDirection").get("sg").asDouble();
        this.windSpeed = node.get("windSpeed").get("sg").asDouble();
        this.windWaveDirection = node.get("windWaveDirection").get("sg").asDouble();
        this.windWaveHeight = node.get("windWaveHeight").get("sg").asDouble();
        this.windWavePeriod = node.get("windWavePeriod").get("sg").asDouble();
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

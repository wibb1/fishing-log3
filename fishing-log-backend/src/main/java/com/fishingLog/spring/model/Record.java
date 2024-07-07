package com.fishingLog.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String success;
    @Column(nullable = false)
    private Integer user_id;
    @Column(nullable = false)
    private Timestamp created_at;
    @Column(nullable = false)
    private Timestamp updated_at;
    @Column(nullable = false)
    private String body;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    private Timestamp datetime;
    @Column
    private Double airTemperature;
    @Column
    private Double pressure;
    @Column
    private Double cloudCover;
    @Column
    private Double gust;
    @Column
    private Double humidity;
    @Column
    private Double visibility;
    @Column
    private String windDirection;
    @Column
    private Double windSpeed;
    @Column
    private Double currentDirection;
    @Column
    private Double currentSpeed;
    @Column
    private String firstType;
    @Column
    private Timestamp firstTime;
    @Column
    private Double firstHeight;
    @Column
    private String secondType;
    @Column
    private Timestamp secondTime;
    @Column
    private Double secondHeight;
    @Column
    private String thirdType;
    @Column
    private Timestamp thirdTime;
    @Column
    private Double thirdHeight;
    @Column
    private String fourthType;
    @Column
    private Timestamp fourthTime;
    @Column
    private Double fourthHeight;
    @Column
    private Timestamp astronomicalDawn;
    @Column
    private Timestamp astronomicalDusk;
    @Column
    private Timestamp civilDawn;
    @Column
    private Timestamp civilDusk;
    @Column
    private Double moonFraction;
    @Column
    private String moonPhase;
    @Column
    private Timestamp moonrise;
    @Column
    private Timestamp moonset;
    @Column
    private Timestamp sunrise;
    @Column
    private Timestamp sunset;
    @Column
    private Timestamp astroTime;
    @Column
    private Double seaLevel;
    @Column
    private String swellDirection;
    @Column
    private Double swellHeight;
    @Column
    private Double swellPeriod;
    @Column
    private String secondarySwellDirection;
    @Column
    private Double secondarySwellHeight;
    @Column
    private Double secondarySwellPeriod;
    @Column
    private String waveDirection;
    @Column
    private Double waveHeight;
    @Column
    private Double wavePeriod;
    @Column
    private String windWaveDirection;
    @Column
    private Double windWaveHeight;
    @Column
    private Double windWavePeriod;
//    bigint "species_id"
//    index ["species_id"], name: "index_records_on_species_id"
//    index ["user_id"], name: "index_records_on_user_id"


    public Record(Long id, String name, String success, Integer user_id, Timestamp created_at, Timestamp updated_at,
                  String body, Double latitude, Double longitude, Timestamp datetime, Double airTemperature,
                  Double pressure, Double cloudCover, Double gust, Double humidity, Double visibility,
                  String windDirection, Double windSpeed, Double currentDirection, Double currentSpeed, String firstType,
                  Timestamp firstTime, Double firstHeight, String secondType, Timestamp secondTime, Double secondHeight,
                  String thirdType, Timestamp thirdTime, Double thirdHeight, String fourthType, Timestamp fourthTime,
                  Double fourthHeight, Timestamp astronomicalDawn, Timestamp astronomicalDusk, Timestamp civilDawn,
                  Timestamp civilDusk, Double moonFraction, String moonPhase, Timestamp moonrise, Timestamp moonset,
                  Timestamp sunrise, Timestamp sunset, Timestamp astroTime, Double seaLevel, String swellDirection,
                  Double swellHeight, Double swellPeriod, String secondarySwellDirection, Double secondarySwellHeight,
                  Double secondarySwellPeriod, String waveDirection, Double waveHeight, Double wavePeriod,
                  String windWaveDirection, Double windWaveHeight, Double windWavePeriod) {
        this.id = id;
        this.name = name;
        this.success = success;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.body = body;
        this.latitude = latitude;
        this.longitude = longitude;
        this.datetime = datetime;
        this.airTemperature = airTemperature;
        this.pressure = pressure;
        this.cloudCover = cloudCover;
        this.gust = gust;
        this.humidity = humidity;
        this.visibility = visibility;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.currentDirection = currentDirection;
        this.currentSpeed = currentSpeed;
        this.firstType = firstType;
        this.firstTime = firstTime;
        this.firstHeight = firstHeight;
        this.secondType = secondType;
        this.secondTime = secondTime;
        this.secondHeight = secondHeight;
        this.thirdType = thirdType;
        this.thirdTime = thirdTime;
        this.thirdHeight = thirdHeight;
        this.fourthType = fourthType;
        this.fourthTime = fourthTime;
        this.fourthHeight = fourthHeight;
        this.astronomicalDawn = astronomicalDawn;
        this.astronomicalDusk = astronomicalDusk;
        this.civilDawn = civilDawn;
        this.civilDusk = civilDusk;
        this.moonFraction = moonFraction;
        this.moonPhase = moonPhase;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.astroTime = astroTime;
        this.seaLevel = seaLevel;
        this.swellDirection = swellDirection;
        this.swellHeight = swellHeight;
        this.swellPeriod = swellPeriod;
        this.secondarySwellDirection = secondarySwellDirection;
        this.secondarySwellHeight = secondarySwellHeight;
        this.secondarySwellPeriod = secondarySwellPeriod;
        this.waveDirection = waveDirection;
        this.waveHeight = waveHeight;
        this.wavePeriod = wavePeriod;
        this.windWaveDirection = windWaveDirection;
        this.windWaveHeight = windWaveHeight;
        this.windWavePeriod = windWavePeriod;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", success='" + success + '\'' +
                ", user_id=" + user_id +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", body='" + body + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", datetime=" + datetime +
                ", airTemperature=" + airTemperature +
                ", pressure=" + pressure +
                ", cloudCover=" + cloudCover +
                ", gust=" + gust +
                ", humidity=" + humidity +
                ", visibility=" + visibility +
                ", windDirection='" + windDirection + '\'' +
                ", windSpeed=" + windSpeed +
                ", currentDirection=" + currentDirection +
                ", currentSpeed=" + currentSpeed +
                ", firstType='" + firstType + '\'' +
                ", firstTime=" + firstTime +
                ", firstHeight=" + firstHeight +
                ", secondType='" + secondType + '\'' +
                ", secondTime=" + secondTime +
                ", secondHeight=" + secondHeight +
                ", thirdType='" + thirdType + '\'' +
                ", thirdTime=" + thirdTime +
                ", thirdHeight=" + thirdHeight +
                ", fourthType='" + fourthType + '\'' +
                ", fourthTime=" + fourthTime +
                ", fourthHeight=" + fourthHeight +
                ", astronomicalDawn=" + astronomicalDawn +
                ", astronomicalDusk=" + astronomicalDusk +
                ", civilDawn=" + civilDawn +
                ", civilDusk=" + civilDusk +
                ", moonFraction=" + moonFraction +
                ", moonPhase='" + moonPhase + '\'' +
                ", moonrise=" + moonrise +
                ", moonset=" + moonset +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", astroTime=" + astroTime +
                ", seaLevel=" + seaLevel +
                ", swellDirection='" + swellDirection + '\'' +
                ", swellHeight=" + swellHeight +
                ", swellPeriod=" + swellPeriod +
                ", secondarySwellDirection='" + secondarySwellDirection + '\'' +
                ", secondarySwellHeight=" + secondarySwellHeight +
                ", secondarySwellPeriod=" + secondarySwellPeriod +
                ", waveDirection='" + waveDirection + '\'' +
                ", waveHeight=" + waveHeight +
                ", wavePeriod=" + wavePeriod +
                ", windWaveDirection='" + windWaveDirection + '\'' +
                ", windWaveHeight=" + windWaveHeight +
                ", windWavePeriod=" + windWavePeriod +
                '}';
    }
}

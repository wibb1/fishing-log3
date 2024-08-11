package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tideStation")
@Setter
@Getter
public class TideStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private Integer stationDistance;
    @Column private Double stationLat;
    @Column private Double stationLng;
    @Column private String stationName;
    @Column private String stationSource;

    @OneToMany(mappedBy = "tideStation")
    private Tide tide;

    public TideStation(JsonNode stationData) {
        this.stationDistance = stationData.get("distance").asInt();
        this.stationLat = stationData.get("lat").asDouble();
        this.stationLng = stationData.get("lng").asDouble();
        this.stationName = stationData.get("name").asText();
        this.stationSource = stationData.get("source").asText();
    }

    public TideStation(Integer stationDistance, Double stationLat, Double stationLng, String stationName, String stationSource) {
        this.stationDistance = stationDistance;
        this.stationLat = stationLat;
        this.stationLng = stationLng;
        this.stationName = stationName;
        this.stationSource = stationSource;
    }
}

package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"id"})
@Entity
@Table(name = "tide_station", uniqueConstraints = {@UniqueConstraint(columnNames = {"station_name"})})
public class TideStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    @Column(name = "station_lat", nullable = false)
    private Double stationLat;

    @Column(name = "station_lng", nullable = false)
    private Double stationLng;

    @Column(name = "station_source", nullable = false)
    private String stationSource;

    public TideStation(JsonNode stationData) {
        this.stationLat = stationData.get("lat").asDouble();
        this.stationLng = stationData.get("lng").asDouble();
        this.stationName = stationData.get("name").asText();
        this.stationSource = stationData.get("source").asText();
    }

    public TideStation(Double stationLat, Double stationLng, String stationName, String stationSource) {
        this.stationLat = stationLat;
        this.stationLng = stationLng;
        this.stationName = stationName;
        this.stationSource = stationSource;
    }
}

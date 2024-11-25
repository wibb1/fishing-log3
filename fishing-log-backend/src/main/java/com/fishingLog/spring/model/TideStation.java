package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

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

    public TideStation(Map<String, Object> node) {
        this.stationLat = (Double) node.get("lat");
        this.stationLng = (Double) node.get("lng");
        this.stationName = (String) node.get("name");
        this.stationSource = (String) node.get("source");
    }

    public TideStation(Double stationLat, Double stationLng, String stationName, String stationSource) {
        this.stationLat = stationLat;
        this.stationLng = stationLng;
        this.stationName = stationName;
        this.stationSource = stationSource;
    }
}

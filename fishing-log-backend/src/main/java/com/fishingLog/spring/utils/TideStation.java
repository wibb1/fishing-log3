package com.fishingLog.spring.utils;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class TideStation implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer stationDistance;
    private Double stationLat;
    private Double stationLng;
    private String stationName;
    private String stationSource;

    public TideStation(JsonNode stationData) {
        this.stationDistance = stationData.get("distance").asInt();
        this.stationLat = stationData.get("lat").asDouble();
        this.stationLng = stationData.get("lng").asDouble();
        this.stationName = stationData.get("name").asText();
        this.stationSource = stationData.get("source").asText();
    }
}

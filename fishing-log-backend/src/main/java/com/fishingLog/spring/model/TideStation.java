package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @Entity @Table(name = "tideStation", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "stationName"})})
public class TideStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column private String stationName;
    @Column private Integer stationDistance;
    @Column private Double stationLat;
    @Column private Double stationLng;
    @Column private String stationSource;

    public TideStation() {}

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

    @Override
    public String toString() {
        return "TideStation{" +
//                "id=" + id +
                ", stationDistance=" + stationDistance +
                ", stationLat=" + stationLat +
                ", stationLng=" + stationLng +
                ", stationName='" + stationName + '\'' +
                ", stationSource='" + stationSource + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TideStation that)) return false;

        if (getStationDistance() != null ? !getStationDistance().equals(that.getStationDistance()) : that.getStationDistance() != null)
            return false;
        if (getStationLat() != null ? !getStationLat().equals(that.getStationLat()) : that.getStationLat() != null)
            return false;
        if (getStationLng() != null ? !getStationLng().equals(that.getStationLng()) : that.getStationLng() != null)
            return false;
        if (getStationName() != null ? !getStationName().equals(that.getStationName()) : that.getStationName() != null)
            return false;
        if (getStationSource() != null ? !getStationSource().equals(that.getStationSource()) : that.getStationSource() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
//        int result = getId() != null ? getId().hashCode() : 0;
//        result = 31 * result + (getStationDistance() != null ? getStationDistance().hashCode() : 0);
        int result = (getStationDistance() != null ? getStationDistance().hashCode() : 0);
        result = 31 * result + (getStationLat() != null ? getStationLat().hashCode() : 0);
        result = 31 * result + (getStationLng() != null ? getStationLng().hashCode() : 0);
        result = 31 * result + (getStationName() != null ? getStationName().hashCode() : 0);
        result = 31 * result + (getStationSource() != null ? getStationSource().hashCode() : 0);
        return result;
    }
}

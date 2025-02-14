package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fishingLog.spring.utils.Conversions;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tide")
public class Tide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double height;

    @Column
    private Instant time;

    @Column
    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private Record record;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tide_station_id", referencedColumnName = "id")
    private TideStation tideStation;

    public void setTimeWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public Tide(Double height, Instant time, String type) {
        this.height = height;
        this.time = time;
        this.type = type;
    }

    public Tide(JsonNode data) {
        this.height = data.get("height").asDouble();
        this.setTimeWithString(data.get("time").asText());
        this.type = data.get("type").asText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tide tide)) return false;

        if (getTideStation() != null ? getTideStation().equals(tide.getTideStation()) : tide.getTideStation() != null)
            return false;
        if (getTime() != null ? !isTimeEqual(tide) : tide.getTime() != null) return false;
        if (getHeight() != null ? !getHeight().equals(tide.getHeight()) : tide.getHeight() != null) return false;
        return (getType() != null ? !getType().equals(tide.getType()) : tide.getType() == null);
    }
    private boolean isTimeEqual(Tide tide) {
        if (this.time == null || tide.time == null) {
            return this.time == tide.time;
        }
        return ChronoUnit.MINUTES.between(this.time, tide.time) <= 30;
    }
    @Override
    public int hashCode() {
        return Objects.hash(getHeight(), time, getType(), getRecord(), getTideStation());
    }
}

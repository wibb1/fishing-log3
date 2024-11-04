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

        if (getHeight() != null ? !getHeight().equals(tide.getHeight()) : tide.getHeight() != null) return false;
        if (getTime() != null ? !isTimeEqual(tide) : tide.getHeight() != null) return false;  // Use a custom method to check time equality
        if (getType() != null ? !getType().equals(tide.getType()) : tide.getType() != null) return false;
        if (getRecord() != null ? !getRecord().equals(tide.getRecord()) : tide.getRecord() != null) return false;
        return getTideStation() != null ? getTideStation().equals(tide.getTideStation()) : tide.getTideStation() == null;
    }
    private boolean isTimeEqual(Tide tide) {
        if (this.time == null || tide.time == null) {
            return this.time == tide.time; // Both are null or one is null
        }
        // Check if the time is within one hour
        return ChronoUnit.MINUTES.between(this.time, tide.time) <= 30;
    }
    @Override
    public int hashCode() {
        return Objects.hash(getHeight(), time, getType(), getRecord(), getTideStation());
    }
}

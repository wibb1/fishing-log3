package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fishingLog.spring.utils.Conversions;
import com.fishingLog.spring.utils.MoonPhase;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode(exclude = {"id", "record"})
@Table(name = "astrological")
public class Astrological {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Instant astronomicalDawn;
    @Column
    private Instant astronomicalDusk;
    @Column
    private Instant civilDawn;
    @Column
    private Instant civilDusk;
    @Column
    private Instant moonrise;
    @Column
    private Instant moonset;
    @Column
    private Instant sunrise;
    @Column
    private Instant sunset;
    @Column
    private Instant time;
    @Column
    private Instant nauticalDawn;
    @Column
    private Instant nauticalDusk;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "text", column = @Column(name = "closest_moon_text")),
            @AttributeOverride(name = "time", column = @Column(name = "closest_moon_time")),
            @AttributeOverride(name = "value", column = @Column(name = "closest_moon_value"))
    })
    private MoonPhase closestMoonPhase;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "text", column = @Column(name = "current_moon_text")),
            @AttributeOverride(name = "time", column = @Column(name = "current_moon_time")),
            @AttributeOverride(name = "value", column = @Column(name = "current_moon_value"))
    })
    private MoonPhase currentMoonPhase;

    @OneToOne(mappedBy = "astrological")
    private Record record;

    private static final ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    public Astrological(JsonNode dataJson) {
        this.astronomicalDawn = Instant.parse(dataJson.get("astronomicalDawn").asText());
        this.astronomicalDusk = Instant.parse(dataJson.get("astronomicalDusk").asText());
        this.civilDawn = Instant.parse(dataJson.get("civilDawn").asText());
        this.civilDusk = Instant.parse(dataJson.get("civilDusk").asText());
        this.moonrise = Instant.parse(dataJson.get("moonrise").asText());
        this.moonset = Instant.parse(dataJson.get("moonset").asText());
        this.sunrise = Instant.parse(dataJson.get("sunrise").asText());
        this.sunset = Instant.parse(dataJson.get("sunset").asText());
        this.time = Instant.parse(dataJson.get("time").asText());
        this.nauticalDawn = Instant.parse(dataJson.get("nauticalDawn").asText());
        this.nauticalDusk = Instant.parse(dataJson.get("nauticalDusk").asText());
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        this.closestMoonPhase = mapper.convertValue(dataJson.get("moonPhase").get("closest"), MoonPhase.class);
        this.currentMoonPhase = mapper.convertValue(dataJson.get("moonPhase").get("current"), MoonPhase.class);
    }

    public Astrological(Map<String, Object> map) {
        this.astronomicalDawn = (Instant) map.get("astronomicalDawn");
        this.astronomicalDusk = (Instant) map.get("astronomicalDusk");
        this.civilDawn = (Instant) map.get("civilDawn");
        this.civilDusk = (Instant) map.get("civilDusk");
        this.moonrise = (Instant) map.get("moonrise");
        this.moonset = (Instant) map.get("moonset");
        this.sunrise = (Instant) map.get("sunrise");
        this.sunset = (Instant) map.get("sunset");
        this.time = (Instant) map.get("time");
        this.nauticalDawn = (Instant) map.get("nauticalDawn");
        this.nauticalDusk = (Instant) map.get("nauticalDusk");
        this.closestMoonPhase = (MoonPhase) map.get("closest");
        this.currentMoonPhase = (MoonPhase) map.get("current");
    }

    public void setTimeWithString(String field, String time) {
        switch (field) {
            case "astronomicalDawn" -> this.astronomicalDawn = Conversions.setTimeWithDateString(time);
            case "astronomicalDusk" -> this.astronomicalDusk = Conversions.setTimeWithDateString(time);
            case "civilDawn" -> this.civilDawn = Conversions.setTimeWithDateString(time);
            case "civilDusk" -> this.civilDusk = Conversions.setTimeWithDateString(time);
            case "moonrise" -> this.moonrise = Conversions.setTimeWithDateString(time);
            case "moonset" -> this.moonset = Conversions.setTimeWithDateString(time);
            case "sunrise" -> this.sunrise = Conversions.setTimeWithDateString(time);
            case "sunset" -> this.sunset = Conversions.setTimeWithDateString(time);
            case "time" -> this.time = Conversions.setTimeWithDateString(time);
            case "nauticalDawn" -> this.nauticalDawn = Conversions.setTimeWithDateString(time);
            case "nauticalDusk" -> this.nauticalDusk = Conversions.setTimeWithDateString(time);
            default -> throw new IllegalArgumentException("Invalid field: " + field);
        }
    }
}

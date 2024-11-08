package com.fishingLog.spring.model;

import com.fasterxml.jackson.core.type.TypeReference;
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
        this(parseJsonToMap(dataJson));
    }

    public Astrological(Map<String, Object> map) {
        this.astronomicalDawn = Instant.parse((String) map.get("astronomicalDawn"));
        this.astronomicalDusk = Instant.parse((String) map.get("astronomicalDusk"));
        this.civilDawn = Instant.parse((String) map.get("civilDawn"));
        this.civilDusk = Instant.parse((String) map.get("civilDusk"));
        this.moonrise = Instant.parse((String) map.get("moonrise"));
        this.moonset = Instant.parse((String) map.get("moonset"));
        this.sunrise = Instant.parse((String) map.get("sunrise"));
        this.sunset = Instant.parse((String) map.get("sunset"));
        this.time = Instant.parse((String) map.get("time"));
        this.closestMoonPhase = (MoonPhase) map.get("closestMoonPhase");
        this.currentMoonPhase = (MoonPhase) map.get("currentMoonPhase");
    }

    private static Map<String, Object> parseJsonToMap(JsonNode json) {
        return mapper.convertValue(json, new TypeReference<>() {});
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
            default -> throw new IllegalArgumentException("Invalid field: " + field);
        }
    }
}

package com.fishingLog.spring.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fishingLog.spring.utils.Conversions;
import com.fishingLog.spring.utils.MoonPhase;
import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="tide_station_id", referencedColumnName = "id")
    private TideStation tideStation;

    @OneToOne(mappedBy = "astrological")
    private Record record;

    private static final ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    public Astrological(JsonNode dataJson, JsonNode metaJson) {
        this(parseJsonToMap(dataJson), new TideStation(metaJson));
    }

    public Astrological(Map<String, Object> map, TideStation tideStation) {
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
        this.tideStation = tideStation;
    }

    private static Map<String, Object> parseJsonToMap(JsonNode json) {
        return mapper.convertValue(json, new TypeReference<>() {});
    }

    public void setAstronomicalDawnWithString(String time) {
        this.astronomicalDawn = Conversions.setTimeWithDateString(time);
    }

    public void setAstronomicalDuskWithString(String time) {
        this.astronomicalDusk = Conversions.setTimeWithDateString(time);
    }

    public void setCivilDawnWithString(String time) {
        this.civilDawn = Conversions.setTimeWithDateString(time);
    }

    public void setCivilDuskWithString(String time) {
        this.civilDusk = Conversions.setTimeWithDateString(time);
    }

    public void setMoonriseWithString(String time) {
        this.moonrise = Conversions.setTimeWithDateString(time);
    }

    public void setMoonsetWithString(String time) {
        this.moonset = Conversions.setTimeWithDateString(time);
    }

    public void setSunriseWithString(String time) {
        this.sunrise = Conversions.setTimeWithDateString(time);
    }

    public void setSunsetWithString(String time) {
        this.sunset = Conversions.setTimeWithDateString(time);
    }

    public void setTimeWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }
}

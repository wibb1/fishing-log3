package com.fishingLog.spring.model;

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
@EqualsAndHashCode(exclude = {"id"})
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
            @AttributeOverride(name = "text", column = @Column(name = "closest_text")),
            @AttributeOverride(name = "time", column = @Column(name = "closest_time")),
            @AttributeOverride(name = "value", column = @Column(name = "closest_value"))
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
    @JoinColumn(name="tideStationId", referencedColumnName = "id")
    private TideStation tideStation;

    @OneToOne(mappedBy = "astrological")
    private Record record;

    public Astrological(JsonNode dataJson, JsonNode metaJson) {
        this.astronomicalDawn = Instant.parse(dataJson.get("astronomicalDawn").asText());
        this.astronomicalDusk = Instant.parse(dataJson.get("astronomicalDusk").asText());
        this.civilDawn = Instant.parse(dataJson.get("civilDawn").asText());
        this.civilDusk = Instant.parse(dataJson.get("civilDusk").asText());
        this.moonrise = Instant.parse(dataJson.get("moonrise").asText());
        this.moonset = Instant.parse(dataJson.get("moonset").asText());
        this.sunrise = Instant.parse(dataJson.get("sunrise").asText());
        this.sunset = Instant.parse(dataJson.get("sunset").asText());
        this.time = Instant.parse(dataJson.get("time").asText());
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        this.closestMoonPhase = mapper.convertValue(dataJson.get("moonPhase").get("closest"), MoonPhase.class);
        this.currentMoonPhase = mapper.convertValue(dataJson.get("moonPhase").get("current"), MoonPhase.class);
        this.tideStation = new TideStation(metaJson);
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
        this.tideStation = (TideStation) map.get("tideStation");
    }

    public void setAstronomicalDawnWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public void setAstronomicalDuskWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public void setCivilDawnWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public void setCivilDuskWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public void setMoonriseWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public void setMoonsetWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public void setSunriseWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public void setSunsetWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public void setTimeWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }
}

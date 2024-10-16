package com.fishingLog.spring.utils;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class MoonPhase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String text;
    private Instant time;
    private Double value;

    public void setTimeWithString(String time) {
        this.time = Instant.parse(time);
    }

    public MoonPhase() {
    }

    public MoonPhase(JsonNode data) {
        this.text = data.get("text").asText();
        this.time = Instant.parse(data.get("time").asText());
        this.value = data.get("value").asDouble();
    }


    public MoonPhase(String text, Instant time, Double value) {
        this.text = text;
        this.time = time;
        this.value = value;
    }
}

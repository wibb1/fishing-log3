package com.fishingLog.spring.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fishingLog.spring.utils.Conversions;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude ={"id",})
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
    @JoinColumn(name = "recordid", referencedColumnName = "id")
    private Record record;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tidestationid", referencedColumnName = "id")
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
}

package com.fishingLog.spring.model;

import com.fishingLog.spring.utils.Conversions;
import com.fishingLog.spring.utils.TideStation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter @Entity @Table(name ="tide")
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
    @OneToOne(mappedBy = "tide")
    private Record record;

    @Embedded
    private TideStation tideStation;

    public void setTimeWithString(String time) {
        this.time = Conversions.setTimeWithDateString(time);
    }

    public Tide() {}

    public Tide(Double height, Instant time, String type, Record record) {
        this.height = height;
        this.time = time;
        this.type = type;
        this.record = record;
    }
}

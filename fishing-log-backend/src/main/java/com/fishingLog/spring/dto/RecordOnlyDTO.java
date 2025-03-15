package com.fishingLog.spring.dto;

import com.fishingLog.spring.model.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class RecordOnlyDTO {
    private Long id;
    private String name;
    private String success;
    private Long anglerId;
    private Instant createdAt;
    private Instant updatedAt;
    private String body;
    private Double latitude;
    private Double longitude;
    private Instant datetime;
    private String timezone;

    RecordOnlyDTO(Record record) {
        this.id = record.getId();
        this.name = record.getName();
        this.success = record.getSuccess();
        this.anglerId = record.getAngler_id();
        this.createdAt = record.getCreated_at();
        this.updatedAt = record.getUpdated_at();
        this.body = record.getBody();
        this.latitude = record.getLatitude();
        this.longitude = record.getLongitude();
        this.datetime = record.getDatetime();
        this.timezone = record.getTimezone();
    }
}

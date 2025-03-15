package com.fishingLog.spring.dto;

import com.fishingLog.spring.model.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class RecordDTO {
    private Long id;
    private String name;
    private String success;
    private Long angler_id;
    private Instant createdAt;
    private Instant updatedAt;
    private String body;
    private Double latitude;
    private Double longitude;
    private Instant datetime;
    private String timezone;
    private Set<AnglerDTO> anglers;
    private WeatherDTO weather;
    private Set<TideDTO> tides;
    private AstrologicalDTO astrological;

    public RecordDTO(Record record) {
        this.id = record.getId();
        this.name = record.getName();
        this.success = record.getSuccess();
        this.angler_id = record.getAngler_id();
        this.createdAt = record.getCreated_at();
        this.updatedAt = record.getUpdated_at();
        this.body = record.getBody();
        this.latitude = record.getLatitude();
        this.longitude = record.getLongitude();
        this.datetime = record.getDatetime();
        this.timezone = record.getTimezone();
        this.anglers = record.getAnglers().stream()
                .map(angler -> new AnglerDTO(angler, false))
                .collect(Collectors.toSet());
        this.weather = new WeatherDTO(record.getWeather());
        this.tides = record.getTides().stream()
                .map(tide -> new TideDTO(tide))
                .collect(Collectors.toSet());
        this.astrological = new AstrologicalDTO(record.getAstrological());
    }

    public static List<RecordDTO> listFromRecords(Set<Record> records) {
        return records.stream()
                .map(RecordDTO::new)
                .collect(Collectors.toList());
    }
}

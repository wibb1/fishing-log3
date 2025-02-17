package com.fishingLog.spring.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String name;
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String success;
    @Column(nullable = false)
    private Long angler_id;
    @Column(nullable = false)
    private Instant created_at;
    @Column(nullable = false)
    private Instant updated_at;
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String body;
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private Double latitude;
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Instant datetime;
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String timezone;

    @ManyToMany(mappedBy = "records")
    private Set<Angler> anglers;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_id", referencedColumnName = "id")
    private Weather weather;

    @ManyToMany(mappedBy = "records")
    private Set<Tide> tides;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "astrological_id", referencedColumnName = "id")
    private Astrological astrological;

    public static class RecordBuilder {
        private String name;
        private String success;
        private Long angler_id;
        private Instant created_at;
        private Instant updated_at;
        private String body;
        private Double latitude;
        private Double longitude;
        private Instant datetime;
        private String timezone;
        private Set<Angler> anglers;
        private Weather weather;
        private Set<Tide> tides = new HashSet<>();
        private Astrological astrological;

        public RecordBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public RecordBuilder setSuccess(String success) {
            this.success = success;
            return this;
        }

        public RecordBuilder setAnglerId(Long angler_id) {
            this.angler_id = angler_id;
            return this;
        }

        public RecordBuilder setCreatedAt(Instant created_at) {
            this.created_at = created_at;
            return this;
        }

        public RecordBuilder setUpdatedAt(Instant updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public RecordBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public RecordBuilder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public RecordBuilder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public RecordBuilder setDatetime(Instant datetime) {
            this.datetime = datetime;
            return this;
        }

        public RecordBuilder setTimezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public RecordBuilder setAnglers(Set<Angler> anglers) {
            this.anglers = anglers;
            return this;
        }

        public RecordBuilder setWeather(Weather weather) {
            this.weather = weather;
            return this;
        }

        public RecordBuilder setTides(Set<Tide> tides) {
            this.tides = tides;
            return this;
        }

        public RecordBuilder setAstrological(Astrological astrological) {
            this.astrological = astrological;
            return this;
        }

        public Record build() {
            Record record = new Record();
            record.name = this.name;
            record.success = this.success;
            record.angler_id = this.angler_id;
            record.created_at = this.created_at;
            record.updated_at = this.updated_at;
            record.body = this.body;
            record.latitude = this.latitude;
            record.longitude = this.longitude;
            record.datetime = this.datetime;
            record.timezone = this.timezone;
            record.anglers = this.anglers;
            record.weather = this.weather;
            record.tides = this.tides;
            record.astrological = this.astrological;
            return record;
        }
    }
}

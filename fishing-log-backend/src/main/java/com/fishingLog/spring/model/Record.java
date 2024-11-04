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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"angler_id", "latitude", "longitude", "datetime", "timezone"})
@ToString
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String success;
    @Column(nullable = false)
    private Integer angler_id;
    @Column(nullable = false)
    private Instant created_at;
    @Column(nullable = false)
    private Instant updated_at;
    @Column(nullable = false)
    private String body;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    private Instant datetime;
    @Column(nullable = false)
    private String timezone;

    @ManyToMany(mappedBy = "records")
    private Set<Angler> anglers;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_id", referencedColumnName = "id")
    private Weather weather;

    @OneToMany(mappedBy = "record", orphanRemoval = true)
    private List<Tide> tides = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "astrological_id", referencedColumnName = "id")
    private Astrological astrological;
}

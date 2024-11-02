package com.fishingLog.spring.model;

import jakarta.persistence.*;
import lombok.*;

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

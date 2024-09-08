package com.fishingLog.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weatherId", referencedColumnName = "id")
    private Weather weather;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tideId", referencedColumnName = "id")
    private Tide tide;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "astrologicalId", referencedColumnName = "id")
    private Astrological astrological;
}

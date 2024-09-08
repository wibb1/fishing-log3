package com.fishingLog.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter @Getter
@Entity
@NoArgsConstructor
@Table(name="species")
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String commonName;
    @Column
    private String scientificName;
    @Column
    private Integer shallowDepth;
    @Column
    private Integer deepDepth;
    @Column
    private Instant createdAt;
    @Column
    private Instant updatedAt;

    public Species(Long id, String commonName, String scientificName, Integer shallowDepth,
                   Integer deepDepth, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.shallowDepth = shallowDepth;
        this.deepDepth = deepDepth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

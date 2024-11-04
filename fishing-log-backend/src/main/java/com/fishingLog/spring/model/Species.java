package com.fishingLog.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Setter @Getter
@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"scientificName"})
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

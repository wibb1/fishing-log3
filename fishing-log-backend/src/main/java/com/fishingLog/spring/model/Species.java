package com.fishingLog.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter @Getter
@Entity
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
    private Timestamp createdAt;
    @Column
    private Timestamp updatedAt;

    public Species(Long id, String commonName, String scientificName, Integer shallowDepth,
                   Integer deepDepth, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.shallowDepth = shallowDepth;
        this.deepDepth = deepDepth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

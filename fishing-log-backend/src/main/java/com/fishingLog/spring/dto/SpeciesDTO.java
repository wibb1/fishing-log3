package com.fishingLog.spring.dto;

import com.fishingLog.spring.model.Species;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDTO {
    private Long id;
    private String commonName;
    private String scientificName;
    private Integer shallowDepth;
    private Integer deepDepth;
    private String createdAt;
    private String updatedAt;

    public SpeciesDTO(Long id, String commonName, String scientificName, Integer shallowDepth, Integer deepDepth) {
        this.id = id;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.shallowDepth = shallowDepth;
        this.deepDepth = deepDepth;
    }

    public SpeciesDTO(Species species) {
        this.id = species.getId();
        this.commonName = species.getCommonName();
        this.scientificName = species.getScientificName();
        this.shallowDepth = species.getShallowDepth();
        this.deepDepth = species.getDeepDepth();
        this.createdAt = species.getCreatedAt().toString();
        this.updatedAt = species.getUpdatedAt().toString();
    }
}

package com.fishingLog.spring.controller;

import com.fishingLog.spring.dto.SpeciesDTO;
import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/species")
public class SpeciesController {
    private final SpeciesService speciesService;

    @Autowired
    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    @GetMapping
    public List<SpeciesDTO> findAllSpecies() {
        List<Species> species = speciesService.findAllSpecies();
        return species.stream()
                .map(species1 -> new SpeciesDTO(species1))
                .toList();
    }

    @GetMapping("/{id}")
    public Optional<SpeciesDTO> findSpeciesById(@PathVariable("id") Long id) {
        Optional<Species> species = speciesService.findSpeciesById(id);
        return Optional.of(new SpeciesDTO(species.get()));
    }

    @PostMapping("/uploadSpecies")
    public SpeciesDTO saveSpecies(Species species) {
        Species savedSpecies = speciesService.saveSpecies(species);
        return new SpeciesDTO(savedSpecies);

    }

    @PostMapping
    public List<SpeciesDTO> saveSpecies(List<Species> species) {
        List<Species> speciesList = speciesService.saveSpecies(species);
        return speciesList.stream()
                .map(species1 -> new SpeciesDTO(species1))
                .toList();
    }

    @PutMapping(path="/{id}")
    public void updateSpecies(@PathVariable Long id, @RequestBody Species species) {
        Optional<Species> current = speciesService.findSpeciesById(id);
        if (current.isPresent()) {
            species.setId(id);
            speciesService.saveSpecies(species);
        }
    }
    @DeleteMapping(path="/{id}")
    public void deleteSpecies(@PathVariable Long id) {
        speciesService.deleteSpecies(id);
    }
}

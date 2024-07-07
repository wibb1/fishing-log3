package com.fishingLog.spring.controller;

import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Species> findSpecies() {
        return speciesService.findSpecies();
    }
    @GetMapping("/{id}")
    public Optional<Species> findSpeciesById(@PathVariable("id") Long id) {
        return speciesService.findSpeciesById(id);
    }
    @PostMapping("/uploadSpecies")
    public Species saveSpecies(Species species) {
        return speciesService.saveSpecies(species);
    }
    @PostMapping
    public List<Species> saveSpecies(List<Species> species) {
        return speciesService.saveSpecies(species);
    }
    @PutMapping(path="/{id}")
    public void updateSpecies(@PathVariable Long id, @RequestBody Species species) {
        Optional<Species> current = speciesService.findSpeciesById(id);
        if (null != current) {
            species.setId(id);
            speciesService.saveSpecies(species);
        }
    }
    @DeleteMapping(path="/{id}")
    public void deleteSpecies(@PathVariable Long id) {
        speciesService.deleteSpecies(id);
    }
}

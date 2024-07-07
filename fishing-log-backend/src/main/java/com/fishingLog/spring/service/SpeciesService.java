package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpeciesService {
    @Autowired public SpeciesRepository speciesRepository;

    public List<Species> findSpecies() {
        return speciesRepository.findAll();
    }

    public Optional<Species> findSpeciesById(Long id) {
        return speciesRepository.findById(id);
    }

    public Species saveSpecies(Species species) {
        return speciesRepository.save(species);
    }

    public List<Species> saveSpecies(List<Species> species) {
        return speciesRepository.saveAll(species);
    }

    public void deleteSpecies(Long id) {
        speciesRepository.deleteById(id);
    }
}

package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpeciesService {
    @Autowired public SpeciesRepository speciesRepository;

    public List<Species> findSpecies() {
        return speciesRepository.findAll();
    }

    public Optional<Species> findSpeciesById(Long id) {
        return speciesRepository.findById(id);
    }

    public Optional<Species> findEqualSpecies(Species a) {
        return speciesRepository.findOne(Example.of(a));
    }

    public Species saveSpecies(Species species) {
        Optional<Species> equalSpecies = findEqualSpecies(species);
        return equalSpecies.orElseGet(() -> speciesRepository.save(species));
    }

    public List<Species> saveSpecies(List<Species> species) {
        List<Species> speciesToSave = species.stream().filter(
                fish -> speciesRepository.findByScientificName(fish.getScientificName()).isPresent()).collect(Collectors.toList()
        );
        return speciesRepository.saveAll(speciesToSave);
    }

    public void deleteSpecies(Long id) {
        speciesRepository.deleteById(id);
    }
}

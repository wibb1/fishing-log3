package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.repository.SpeciesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpeciesService {
    private static final Logger logger = LoggerFactory.getLogger(SpeciesService.class);
    private final SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public List<Species> findAllSpecies() {
        logger.info("Fetching all species");
        return speciesRepository.findAll();
    }

    public Optional<Species> findSpeciesById(Long id) {
        logger.info("Fetching species with ID: {}", id);
        return speciesRepository.findById(id);
    }

    public Optional<Species> findEqualSpecies(Species species) {
        logger.info("Checking for equal species in the database");
        return speciesRepository.findOne(Example.of(species));
    }

    public Species saveSpecies(Species species) {
        if (species == null) {
            logger.error("Attempted to save a null species");
            throw new IllegalArgumentException("Species cannot be null");
        }
        logger.info("Saving species with scientific name: {}", species.getScientificName());
        return findEqualSpecies(species)
                .orElseGet(() -> speciesRepository.save(species));
    }

    public List<Species> saveSpecies(List<Species> speciesList) {
        if (speciesList == null || speciesList.isEmpty()) {
            logger.warn("Species list cannot be null or empty");
            throw new IllegalArgumentException("Species list cannot be null or empty");
        }
        logger.info("Saving a list of species");
        List<Species> speciesToSave = speciesList.stream()
                .filter(fish -> speciesRepository.findByScientificName(fish.getScientificName()).isEmpty())
                .collect(Collectors.toList());
        return speciesRepository.saveAll(speciesToSave);
    }

    public void deleteSpecies(Long id) {
        logger.info("Species not found with ID: {}", id);
        if (!speciesRepository.existsById(id)) {
            logger.error("Species with ID {} not found", id);
            throw new IllegalArgumentException("Species not found with ID: " + id);
        }
        speciesRepository.deleteById(id);
        logger.info("Species with ID {} successfully deleted", id);
    }
}

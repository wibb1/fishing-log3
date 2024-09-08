package com.fishingLog.spring.repository;

import com.fishingLog.spring.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Optional<Species> findByScientificName(String scientificName);
}

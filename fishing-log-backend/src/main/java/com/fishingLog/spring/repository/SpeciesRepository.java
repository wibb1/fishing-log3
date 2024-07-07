package com.fishingLog.spring.repository;

import com.fishingLog.spring.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
}

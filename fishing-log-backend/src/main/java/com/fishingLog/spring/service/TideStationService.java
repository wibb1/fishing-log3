package com.fishingLog.spring.service;

import com.fishingLog.spring.model.TideStation;
import com.fishingLog.spring.repository.TideStationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TideStationService {

    private static final Logger logger = LoggerFactory.getLogger(TideStationService.class);

    private TideStationRepository tideStationRepository;

    public TideStationService(TideStationRepository tideStationRepository) {
        this.tideStationRepository = tideStationRepository;
    }

    public List<TideStation> findAllTideStations() {
        logger.info("Fetching all tide stations");
        return tideStationRepository.findAll();
    }

    public Optional<TideStation> findTideStationByName(String stationName) {
        if (stationName == null || stationName.isBlank()) {
            logger.error("Station name cannot be null or blank");
            throw new IllegalArgumentException("Station name cannot be null or blank");
        }
        logger.info("Finding tide station by name: {}", stationName);
        return tideStationRepository.findByStationName(stationName);
    }

    public Optional<TideStation> findTideStationById(Long id) {
        validateTideStationId(id);
        logger.info("Finding tide station by ID: {}", id);
        return tideStationRepository.findById(id);
    }

    public Optional<TideStation> findEqualTideStation(TideStation tideStation) {
        validateTideStation(tideStation);
        logger.info("Finding tide station by example: {}", tideStation);
        return tideStationRepository.findOne(Example.of(tideStation));
    }

    public TideStation saveTideStation(TideStation tideStation) {
        validateTideStation(tideStation);
        logger.info("Saving tide station: {}", tideStation);
        return findEqualTideStation(tideStation).orElseGet(() -> tideStationRepository.save(tideStation));
    }

    public void deleteTideStation(Long id) {
        validateTideStationId(id);
        if (!tideStationRepository.existsById(id)) {
            logger.error("Tide station with ID {} not found", id);
            throw new IllegalArgumentException("Tide station with ID " + id + " not found");
        }
        logger.info("Deleting tide station with ID: {}", id);
        tideStationRepository.deleteById(id);
    }

    public void setTideStationRepository(TideStationRepository tideStationRepository) {
        this.tideStationRepository = tideStationRepository;
    }

    private void validateTideStationId(Long id) {
        if (id == null) {
            logger.error("Station ID cannot be null");
            throw new IllegalArgumentException("Station ID cannot be null");
        }
    }

    private void validateTideStation(TideStation tideStation) {
        if (tideStation == null) {
            logger.error("TideStation object cannot be null");
            throw new IllegalArgumentException("TideStation object cannot be null");
        }
    }

}

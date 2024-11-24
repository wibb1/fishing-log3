package com.fishingLog.spring.service;

import com.fishingLog.spring.exception.DataConversionException;
import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.repository.AstrologicalRepository;
import com.fishingLog.spring.utils.StormGlassAstrologicalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AstrologicalService {
    private static final Logger logger = LoggerFactory.getLogger(AstrologicalService.class);
    private AstrologicalRepository astrologicalRepository;
    private StormGlassAstrologicalConverter astrologicalConverter;

    public AstrologicalService(AstrologicalRepository astrologicalRepository, StormGlassAstrologicalConverter astrologicalConverter) {
        this.astrologicalRepository = astrologicalRepository;
        this.astrologicalConverter = astrologicalConverter;
    }

    public List<Astrological> findAllAstrological() {
        return astrologicalRepository.findAll();
    }

    public Optional<Astrological> findAstrologicalById(Long id) {
        return astrologicalRepository.findById(id);
    }

    public Optional<Astrological> findEqualAstrological(Astrological astrological ) {
        validateAstrological(astrological );
        return astrologicalRepository.findOne(Example.of(astrological ));
    }

    public Astrological saveAstrological(Astrological astrological) {
        validateAstrological(astrological);
        logger.info("Saving new astrological {}", astrological);
        return findEqualAstrological(astrological).orElseGet(() -> astrologicalRepository.save(astrological));
    }

    public void deleteAstrological(Long id) {
        astrologicalRepository.deleteById(id);
    }

    public Astrological updateAstrological(Astrological astrologicalDetails) {
        validateAstrological(astrologicalDetails);
        Astrological existingAstrological = astrologicalRepository.findById(astrologicalDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("Astrological not found with id: " + astrologicalDetails.getId()));

        existingAstrological.setAstronomicalDawn(astrologicalDetails.getAstronomicalDawn());
        existingAstrological.setAstronomicalDusk(astrologicalDetails.getAstronomicalDusk());
        existingAstrological.setCivilDawn(astrologicalDetails.getCivilDawn());
        existingAstrological.setCivilDusk(astrologicalDetails.getCivilDusk());
        existingAstrological.setClosestMoonPhase(astrologicalDetails.getClosestMoonPhase());
        existingAstrological.setCurrentMoonPhase(astrologicalDetails.getCurrentMoonPhase());
        existingAstrological.setMoonrise(astrologicalDetails.getMoonrise());
        existingAstrological.setMoonset(astrologicalDetails.getMoonset());
        existingAstrological.setRecord(astrologicalDetails.getRecord());
        existingAstrological.setSunrise(astrologicalDetails.getSunrise());
        existingAstrological.setSunset(astrologicalDetails.getSunset());
        existingAstrological.setTime(astrologicalDetails.getTime());

        logger.info("Updating astrological record with id: {}", astrologicalDetails.getId());
        return astrologicalRepository.save(existingAstrological);
    }

    public Astrological createAstrological(String astrologicalRawData) {
        Map<String, Object> astrologicalData;
        try {
            astrologicalData = astrologicalConverter.dataConverter(astrologicalRawData);
        } catch (IOException e) {
            logger.error("Failed to convert astrological data", e);
            throw new DataConversionException("Failed to convert astrological data", e);
        }

        Astrological newAstrological = new Astrological(astrologicalData);
        return saveAstrological(newAstrological);
    }

    public void setAstrologicalRepository(AstrologicalRepository astrologicalRepository) {
        this.astrologicalRepository = astrologicalRepository;
    }

    public void setAstrologicalConverter(StormGlassAstrologicalConverter astrologicalConverter) {
        this.astrologicalConverter = astrologicalConverter;
    }

    private void validateAstrological(Astrological  astrological) {
        if (astrological == null) {
            logger.error("Astrological object cannot be null");
            throw new IllegalArgumentException("Angler object cannot be null");
        }
    }
}

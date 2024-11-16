package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.repository.AstrologicalRepository;
import com.fishingLog.spring.utils.StormGlassAstrologicalConverter;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AstrologicalService {
    private final AstrologicalRepository astrologicalRepository;
    private final StormGlassAstrologicalConverter astrologicalConverter;

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

    public Optional<Astrological> findEqualAstrological(Astrological a) {
        return astrologicalRepository.findOne(Example.of(a));
    }

    public Astrological saveAstrological(Astrological astrological) {
        return findEqualAstrological(astrological).orElseGet(() -> astrologicalRepository.save(astrological));
    }

    public void deleteAstrological(Long id) {
        astrologicalRepository.deleteById(id);
    }

    public Astrological updateAstrological(Astrological astrologicalDetails) {

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

        return astrologicalRepository.save(existingAstrological);
    }

    public Astrological createAstrological(String astrologicalRawData) {
        Map<String, Object> astrologicalData;
        try {
            astrologicalData = astrologicalConverter.dataConverter(astrologicalRawData);
        } catch (IOException e) {
            throw new RuntimeException("Error converting astrological data", e); // TODO - logger to record error
        }

        Astrological newAstrological = new Astrological(astrologicalData);
        return saveAstrological(newAstrological);
    }
}

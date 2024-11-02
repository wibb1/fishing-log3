package com.fishingLog.spring.service;

import com.fishingLog.spring.model.TideStation;
import com.fishingLog.spring.repository.TideStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TideStationService {
    @Autowired
    public TideStationRepository tideStationRepository;

    public List<TideStation> findAllTideStation() {
        return tideStationRepository.findAll();
    }

    public Optional<TideStation> findTideStationByName(String stationName) {
        return tideStationRepository.findByStationName(stationName);
    }

    public Optional<TideStation> findTideStationById(Long id) {
        return tideStationRepository.findById(id);
    }

    public Optional<TideStation> findEqualTideStation(TideStation tideStation) {
        return tideStationRepository.findOne(Example.of(tideStation));
    }

    public TideStation saveTideStation(TideStation tideStation) {
        Optional<TideStation> equalTideStation = findEqualTideStation(tideStation);
        return equalTideStation.orElseGet(() -> tideStationRepository.save(tideStation));
    }

    public void deleteTideStation(Long id) {
        tideStationRepository.deleteById(id);
    }
}

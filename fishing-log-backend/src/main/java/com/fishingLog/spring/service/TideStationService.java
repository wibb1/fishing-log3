package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.TideStation;
import com.fishingLog.spring.repository.TideRepository;
import com.fishingLog.spring.repository.TideStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TideStationService {
    @Autowired
    public TideStationRepository tideStationRepository;
    public List<TideStation> findAllRecords() {
        return tideStationRepository.findAll();
    }

    public Optional<TideStation> findTideStation(Long id) {
        return tideStationRepository.findById(id);
    }

    public TideStation saveTideStation(TideStation tideStation) {
        return tideStationRepository.save(tideStation);
    }

    public void deleteTideStation(Long id) {
        tideStationRepository.deleteById(id);
    }
}

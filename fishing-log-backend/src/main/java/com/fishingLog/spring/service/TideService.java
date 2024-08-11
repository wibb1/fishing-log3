package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.repository.TideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TideService {
    @Autowired
    public TideRepository tideRepository;

    public List<Tide> findAllRecords() {
        return tideRepository.findAll();
    }

    public Optional<Tide> findTide(Long id) {
        return tideRepository.findById(id);
    }

    public Tide saveTide(Tide tide) {
        return tideRepository.save(tide);
    }

    public void deleteTide(Long id) {
        tideRepository.deleteById(id);
    }
}

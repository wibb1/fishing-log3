package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.repository.TideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TideService {
    @Autowired
    public TideRepository tideRepository;

    public List<Tide> findAllTide() {
        return tideRepository.findAll();
    }

    public Optional<Tide> findTideById(Long id) {
        return tideRepository.findById(id);
    }

    public Optional<Tide> findEqualTide(Tide tide) {
        return tideRepository.findOne(Example.of(tide));
    }

    public Tide saveTide(Tide tide) {
        Optional<Tide> equalTide = findEqualTide(tide);
        return equalTide.orElseGet(() -> tideRepository.save(tide));
    }

    public void deleteTide(Long id) {
        tideRepository.deleteById(id);
    }
}

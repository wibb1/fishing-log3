package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.repository.AnglerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnglerService {
    @Autowired
    public AnglerRepository anglerRepository;

    public List<Angler> findAllAnglers() {
        return anglerRepository.findAll();
    }

    public Optional<Angler> findAngler(Long id) {
        return anglerRepository.findById(id);
    }

    public Angler saveAngler(Angler angler) {
        return anglerRepository.save(angler);
    }

    public void deleteAngler(Long id) {
        anglerRepository.deleteById(id);
    }
}

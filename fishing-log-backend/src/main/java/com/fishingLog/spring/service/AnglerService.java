package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.repository.AnglerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        Optional<Angler> savedAngler = anglerRepository.findByEmail(angler.getEmail());
        if(savedAngler.isPresent()) {
            throw new DataIntegrityViolationException("Angler already exist with given email:" + angler.getEmail());
        }
        return anglerRepository.save(angler);
    }

    public Angler updateAngler(Angler anglerDetails) {
        Angler existingAngler = anglerRepository.findById(anglerDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("Angler not found with id: " + anglerDetails.getId()));
        Optional<Angler> anglerWithEmail = anglerRepository.findByEmail(anglerDetails.getEmail());

        if (anglerWithEmail.isPresent() && !anglerWithEmail.get().getId().equals(anglerDetails.getId())) {
            throw new DataIntegrityViolationException("Angler already exists with given email: " + anglerDetails.getEmail());
        }

        existingAngler.setFirstName(anglerDetails.getFirstName());
        existingAngler.setLastName(anglerDetails.getLastName());
        existingAngler.setUsername(anglerDetails.getUsername());
        existingAngler.setEmail(anglerDetails.getEmail());
        existingAngler.setRole(anglerDetails.getRole());
        existingAngler.setEncryptedPassword(anglerDetails.getEncryptedPassword());
        existingAngler.setCreatedAt(anglerDetails.getCreatedAt());
        existingAngler.setUpdatedAt(anglerDetails.getUpdatedAt());

        return anglerRepository.save(existingAngler);
    }

    public void deleteAngler(Long id) {
        anglerRepository.deleteById(id);
    }
}

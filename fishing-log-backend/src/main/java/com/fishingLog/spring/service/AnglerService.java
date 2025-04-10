package com.fishingLog.spring.service;

import com.fishingLog.spring.exception.EmailAlreadyExistsException;
import com.fishingLog.spring.exception.ResourceNotFoundException;
import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.repository.AnglerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AnglerService {
    private static final Logger logger = LoggerFactory.getLogger(AnglerService.class);
    private final AnglerRepository anglerRepository;
    private final PasswordEncoder passwordEncoder;

    public AnglerService(AnglerRepository anglerRepository, PasswordEncoder passwordEncoder) {
        this.anglerRepository = anglerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Angler> findAllAnglers() {
        logger.info("Fetching all anglers");
        return anglerRepository.findAll();
    }

    public Optional<Angler> findAnglerById(Long id) {
        logger.info("Fetching angler with id: {}", id);
        return anglerRepository.findById(id);
    }

    public Angler saveAngler(Angler angler) {
        validateAngler(angler);
        anglerRepository.findByEmail(angler.getEmail()).ifPresent(existingAngler -> {
            logger.error("Angler already exists with email: {}", angler.getEmail());
            throw new EmailAlreadyExistsException("Angler already exists with email: " + angler.getEmail());
        });

        angler.setPassword(passwordEncoder.encode(angler.getPassword()));

        logger.info("Saving angler with email: {}", angler.getEmail());
        return anglerRepository.save(angler);
    }

    public Angler updateAngler(Angler anglerDetails) {
        validateAngler(anglerDetails);

        Angler existingAngler = anglerRepository.findById(anglerDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("Angler not found with id: " + anglerDetails.getId()));

        anglerRepository.findByEmail(anglerDetails.getEmail()).ifPresent(anglerWithEmail -> {
            if (!anglerWithEmail.getId().equals(anglerDetails.getId())) {
                logger.error("Email already in use by another angler: {}", anglerDetails.getEmail());
                throw new EmailAlreadyExistsException("Email already in use by another angler: " + anglerDetails.getEmail());
            }
        });

        if (Objects.nonNull(anglerDetails.getFirstName())) existingAngler.setFirstName(anglerDetails.getFirstName());
        if (Objects.nonNull(anglerDetails.getLastName())) existingAngler.setLastName(anglerDetails.getLastName());
        if (Objects.nonNull(anglerDetails.getUsername())) existingAngler.setUsername(anglerDetails.getUsername());
        if (Objects.nonNull(anglerDetails.getEmail())) existingAngler.setEmail(anglerDetails.getEmail());
        if (Objects.nonNull(anglerDetails.getRoles())) existingAngler.setRoles(anglerDetails.getRoles());
        if (Objects.nonNull(anglerDetails.getPassword()))
            existingAngler.setPassword(passwordEncoder.encode(anglerDetails.getPassword()));
        if (Objects.nonNull(anglerDetails.getUpdatedAt())) existingAngler.setUpdatedAt(anglerDetails.getUpdatedAt());

        logger.info("Updating angler with id: {}", anglerDetails.getId());
        return anglerRepository.save(existingAngler);
    }

    public void deleteAngler(Long id) {
        logger.info("Deleting angler with id: {}", id);

        if (!anglerRepository.existsById(id)) {
            logger.error("Angler not found with id: {}", id);
            throw new ResourceNotFoundException("Angler not found with id: " + id);
        }
        anglerRepository.deleteById(id);
        logger.info("Angler with id: {} successfully deleted", id);
    }

    private void validateAngler(Angler angler) {
        if (angler == null) {
            logger.error("Angler object cannot be null");
            throw new IllegalArgumentException("Angler object cannot be null");
        }

        if (angler.getEmail() == null || angler.getEmail().isBlank()) {
            logger.error("Angler email cannot be null or blank");
            throw new IllegalArgumentException("Angler email cannot be null or blank");
        }
    }
}

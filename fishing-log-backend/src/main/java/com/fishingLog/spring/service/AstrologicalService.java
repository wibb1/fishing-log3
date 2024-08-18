package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.repository.AstrologicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AstrologicalService {
        @Autowired
        public AstrologicalRepository astrologicalRepository;

        public List<Astrological> findAllAstrologicals() {
            return astrologicalRepository.findAll();
        }

        public Optional<Astrological> findAstrological(Long id) {
            return astrologicalRepository.findById(id);
        }

        public Astrological saveAstrological(Astrological astrological) {
            return astrologicalRepository.save(astrological);
        }

        public void deleteAstrological(Long id) {
            astrologicalRepository.deleteById(id);
        }
}
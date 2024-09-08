package com.fishingLog.spring.repository;

import com.fishingLog.spring.model.Angler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnglerRepository extends JpaRepository<Angler, Long> {
    Optional<Angler> findByEmail(String email);
}

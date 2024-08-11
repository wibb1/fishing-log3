package com.fishingLog.spring.repository;

import com.fishingLog.spring.model.Astrological;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AstrologicalRepository extends JpaRepository<Astrological, Long> {
}

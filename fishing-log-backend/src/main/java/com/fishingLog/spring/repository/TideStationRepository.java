package com.fishingLog.spring.repository;

import com.fishingLog.spring.model.TideStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TideStationRepository extends JpaRepository<TideStation, Long> {
    Optional<TideStation> findByStationName(String stationName);
}

package com.fishingLog.spring.repository;

import com.fishingLog.spring.model.Tide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TideRepository extends JpaRepository<Tide, Long> {
}

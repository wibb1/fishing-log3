package com.fishingLog.spring.repository;

import com.fishingLog.spring.model.Angler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnglerRepository extends JpaRepository<Angler, Long> {
}

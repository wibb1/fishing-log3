package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.TideStation;
import com.fishingLog.spring.repository.TideRepository;
import com.fishingLog.spring.utils.StormGlassTideConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class TideService {
    private static final Logger logger = LoggerFactory.getLogger(TideService.class);

    private TideStationService tideStationService;

    private TideRepository tideRepository;
    private StormGlassTideConverter tideConverter;

    public TideService(TideRepository tideRepository, StormGlassTideConverter tideConverter, TideStationService tideStationService) {
        this.tideRepository = tideRepository;
        this.tideConverter = tideConverter;
        this.tideStationService = tideStationService;
    }

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
        return findEqualTide(tide).orElseGet(() -> tideRepository.save(tide));
    }

    public void deleteTide(Long id) {
        tideRepository.deleteById(id);
    }

    public Set<Tide> createTides(String tideRawData) {
        Map<String, Object> tideData;
        try {
            tideData = tideConverter.dataConverter(tideRawData);
        } catch (IOException e) {
            logger.error("Error converting tide data: {}", e.getMessage(), e);
            throw new RuntimeException("Error converting tide data", e);
        }

        List<String> tideNames = List.of("firstTide", "secondTide", "thirdTide", "fourthTide");
        TideStation tideStation = null;
        if (tideData.containsKey("station")) {
            tideStation = (TideStation) tideData.get("station");
            tideStation = tideStationService.saveTideStation(tideStation);
            logger.info("Tide station saved: {}", tideStation);
        }
        Set<Tide> tides = new HashSet<>();
        for (String tideName : tideNames) {
            if (tideData.containsKey(tideName)) {
                Tide tide = (Tide) tideData.get(tideName);
                if (tideStation != null) tide.setTideStation(tideStation);
                tide = saveTide(tide);
                tides.add(tide);
                logger.info("Tide created: {}", tide);
            } else {
                logger.info("Missing tide data for: {}", tideName);
            }
        }
        return tides;
    }

    public void setTideStationService(TideStationService tideStationService) {
        this.tideStationService = tideStationService;
    }

    public void setTideRepository(TideRepository tideRepository) {
        this.tideRepository = tideRepository;
    }

    public void setTideConverter(StormGlassTideConverter tideConverter) {
        this.tideConverter = tideConverter;
    }
}

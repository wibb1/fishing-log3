package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.TideStation;
import com.fishingLog.spring.repository.TideRepository;
import com.fishingLog.spring.utils.StormGlassTideConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TideService {
    @Autowired
    public TideRepository tideRepository;

    @Autowired
    public TideStationService tideStationService;

    private final StormGlassTideConverter tideConverter = new StormGlassTideConverter();

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

    public List<Tide> createTides(String tideRawData) {
        Map<String, Object> tideData;
        try {
            tideData = tideConverter.dataConverter(tideRawData);
        } catch (IOException e) {
            throw new RuntimeException("Error converting tide data", e);// TODO - logger to record error
        }

        List<String> tideNames = List.of("firstTide", "secondTide", "thirdTide", "fourthTide");
        TideStation tideStation = null;
        if (tideData.containsKey("station")) {
            tideStation = (TideStation) tideData.get("station");
            tideStation = tideStationService.saveTideStation(tideStation);
        }
        List<Tide> tides = new ArrayList<>();
        for (String tideName : tideNames) {
            if (tideData.containsKey(tideName)) {
                Tide tide = (Tide) tideData.get(tideName);
                if (tideStation != null) tide.setTideStation(tideStation);
                tide = saveTide(tide);
                tides.add(tide);
            }
        }
        return tides;
    }
}

package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.model.Species;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.RecordRepository;
import com.fishingLog.spring.utils.ApiResponse;
import com.fishingLog.spring.utils.StormGlassApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RecordService {
    private StormGlassApiService apiService;
    @Autowired
    public RecordRepository recordRepository;

    private WeatherService weatherService;
    private TideService tideService;
    private AstrologicalService astrologicalService;
    private SpeciesService speciesService;

    public RecordService(WeatherService weatherService, TideService tideService, AstrologicalService astrologicalService, StormGlassApiService apiService, SpeciesService speciesService) {
        this.weatherService = weatherService;
        this.tideService = tideService;
        this.astrologicalService = astrologicalService;
        this.apiService = apiService;
        this.speciesService = speciesService;
    }

    public List<Record> findAllRecords() {
        return recordRepository.findAll();
    }

    public Optional<Record> findRecordById(Long id) {
        return recordRepository.findById(id);
    }

    public Optional<Record> findEqualRecord(Record a) {
        return recordRepository.findOne(Example.of(a));
    }

    public Record saveRecord(Record record) {
        return findEqualRecord(record).orElseGet(() -> recordRepository.save(record));
    }

    public List<Record> saveRecord(List<Record> record) {
        return recordRepository.saveAll(record);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    public Record createRecordWithRelatedEntities(Record record) {
        List<ApiResponse> responses = apiService.obtainData(record.getDatetime(), record.getLatitude(), record.getLongitude());
        System.out.println("apiService = " + apiService);
        if (responses == null || responses.size() != 3) {
            throw new IllegalArgumentException("Incorrect number of responses from Stormglass");
        }

        ApiResponse weatherResponse = responses.get(0);
        ApiResponse astrologicalResponse = responses.get(1);
        ApiResponse tideResponse = responses.get(2);

        if (weatherResponse == null || weatherResponse.getBody().isEmpty()) {
            throw new IllegalArgumentException("Weather data is empty");
        }
        if (astrologicalResponse == null || astrologicalResponse.getBody().isEmpty()) {
            throw new IllegalArgumentException("Astrological data is empty");
        }
        if (tideResponse == null || tideResponse.getBody().isEmpty()) {
            throw new IllegalArgumentException("Tide data is empty");
        }

        Weather weather = weatherService.createWeather(responses.get(0).getBody());
        Astrological astrological = astrologicalService.createAstrological(responses.get(1).getBody());
        Set<Tide> tides = tideService.createTides(responses.get(2).getBody());

        if (tides == null || tides.isEmpty()) {
            throw new IllegalArgumentException("Tide data is empty or null");
        }

        Angler currentAngler = getCurrentAngler()
                .orElseThrow(() -> new IllegalStateException("No authenticated user found"));

        Species species = speciesService.createSpecies(record.getSpecies());

        Record newRecord = new Record.RecordBuilder()
                .setName(record.getName())
                .setSuccess(record.getSuccess())
                .setAnglerId(currentAngler.getId())
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now())
                .setBody(record.getBody())
                .setLatitude(record.getLatitude())
                .setLongitude(record.getLongitude())
                .setDatetime(record.getDatetime())
                .setTimezone(record.getTimezone())
                .setWeather(weather)
                .setTides(tides)
                .setAstrological(astrological)
                .setSpecies(species)
                .build();

        return recordRepository.save(newRecord);
    }

    public Optional<Angler> getCurrentAngler() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Angler) {
            return Optional.of((Angler) principal);
        }
        return Optional.empty();
    }

    public void setApiService(StormGlassApiService apiService) {
        this.apiService = apiService;
    }

    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void setTideService(TideService tideService) {
        this.tideService = tideService;
    }

    public void setAstrologicalService(AstrologicalService astrologicalService) {
        this.astrologicalService = astrologicalService;
    }

    public void setSpeciesService(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }
}

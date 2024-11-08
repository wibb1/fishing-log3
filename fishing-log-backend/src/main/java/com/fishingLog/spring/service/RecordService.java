package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.Weather;
import com.fishingLog.spring.repository.RecordRepository;
import com.fishingLog.spring.utils.ApiResponse;
import com.fishingLog.spring.utils.StormGlassApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    @Autowired
    public RecordRepository recordRepository;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private TideService tideService;

    @Autowired
    private AstrologicalService astrologicalService;

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
        Optional<Record> equalRecord = findEqualRecord(record);
        return equalRecord.orElseGet(() -> recordRepository.save(record));
    }

    public List<Record> saveRecord(List<Record> record) {
        return recordRepository.saveAll(record);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    public Record createRecordWithRelatedEntities(Record record) {
        // Get data from the StormGlass API
        StormGlassApiService apiService = new StormGlassApiService(record.getDatetime(), record.getLatitude(), record.getLongitude());
        List<ApiResponse> responses = apiService.obtainData();

        // Use the services to format and save the data
        Weather weather = weatherService.createWeather(responses.get(0).getBody());
        Astrological astrological = astrologicalService.createAstrological(responses.get(1).getBody());
        List<Tide> tides = tideService.createTides(responses.get(2).getBody());

        // Save the new record
        Record newRecord = new Record.RecordBuilder()
                .setName(record.getName())
                .setSuccess(record.getSuccess())
                .setAnglerId(getCurrentUser().getId())
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
                .build();

        return recordRepository.save(newRecord);
    }

    private Angler getCurrentUser() {
        return new Angler();
    }
}

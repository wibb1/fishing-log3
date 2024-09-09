package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    @Autowired
    public RecordRepository recordRepository;

    public List<Record> findAllRecords() {
        return recordRepository.findAll();
    }

    public Optional<Record> findRecord(Long id) {
        return recordRepository.findById(id);
    }

    public Optional<Record> findEqualRecord(Record a) {
        return recordRepository.findOne(Example.of(a));
    }

    public Record saveRecord(Record astrological) {
        Optional<Record> equalRecord = findEqualRecord(astrological);
        return equalRecord.orElseGet(() -> recordRepository.save(astrological));
    }

    public List<Record> saveRecord(List<Record> record) {
        return recordRepository.saveAll(record);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }
}

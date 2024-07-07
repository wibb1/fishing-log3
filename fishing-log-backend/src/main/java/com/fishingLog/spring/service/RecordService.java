package com.fishingLog.spring.service;

import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }

    public List<Record> saveRecord(List<Record> record) {
        return recordRepository.saveAll(record);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }
}

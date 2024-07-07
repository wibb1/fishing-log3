package com.fishingLog.spring.controller;

import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/records")
public class RecordController {
    private final RecordService recordService;
    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }
    @GetMapping
    public List<Record> getAllRecords() {
        return recordService.findAllRecords();
    }
    @GetMapping("/{id}")
    public Optional<Record> getRecordById(@PathVariable("id") Long id) {
        return recordService.findRecord(id);
    }
    @PostMapping("/uploadRecords")
    public List<Record> createRecords(@RequestBody List<Record> records) {
        return recordService.saveRecord(records);
    }
    @PostMapping
    public Record createRecord(@RequestBody Record record) {
        return recordService.saveRecord(record);
    }
    @PutMapping(path="/{id}")
    public void updateRecord(@PathVariable Long id, @RequestBody Record record) {
        Optional<Record> current = recordService.findRecord(id);
        if (null != current) {
            record.setId(id);
            recordService.saveRecord(record);
        }
    }
    @DeleteMapping(path="/{id}")
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }
}

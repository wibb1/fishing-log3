package com.fishingLog.spring.controller;

import com.fishingLog.spring.dto.RecordDTO;
import com.fishingLog.spring.model.Record;
import com.fishingLog.spring.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<RecordDTO> getAllRecords() {
        List<Record> records = recordService.findAllRecords();
        return records.stream()
                .map(record -> new RecordDTO(record))
                .toList();
    }

    @GetMapping("/{id}")
    public Optional<RecordDTO> getRecordById(@PathVariable("id") Long id) {
        Optional<Record> records = recordService.findRecordById(id);
        return records.map(record -> new RecordDTO(record));
    }

    @PostMapping("/uploadRecords")
    public List<RecordDTO> createRecords(@RequestBody List<Record> records) {
        List<Record> returnedRcords = recordService.saveRecord(records);
        return returnedRcords.stream()
                .map(record -> new RecordDTO(record))
                .toList();
    }

    @PostMapping("/saveRecord")
    public RecordDTO createRecord(@RequestBody Record record) {
        return new RecordDTO(recordService.createRecordWithRelatedEntities(record));
    }

    @PutMapping(path = "/{id}")
    public void updateRecord(@PathVariable Long id, @RequestBody Record record) {
        Optional<Record> current = recordService.findRecordById(id);
        if (current.isPresent()) {
            record.setId(id);
            recordService.saveRecord(record);
        }
    }
    @DeleteMapping(path="/{id}")
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }
}
